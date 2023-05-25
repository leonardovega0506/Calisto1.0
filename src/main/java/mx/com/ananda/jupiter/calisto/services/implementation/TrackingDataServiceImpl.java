package mx.com.ananda.jupiter.calisto.services.implementation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import mx.com.ananda.jupiter.calisto.model.estafeta.dto.*;
import mx.com.ananda.jupiter.calisto.model.estafeta.entity.HistoriaModel;
import mx.com.ananda.jupiter.calisto.model.estafeta.entity.TrakingDataModel;
import mx.com.ananda.jupiter.calisto.repository.IHistoryRepository;
import mx.com.ananda.jupiter.calisto.repository.ITrackingRepository;
import mx.com.ananda.jupiter.calisto.services.interfaces.ITrackingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrackingDataServiceImpl implements ITrackingDataService {

    @Autowired
    private ITrackingRepository iTracking;

    @Autowired
    private IHistoryRepository iHistory;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.external.service.base-url}")
    private String basePath;

    @Override
    public List<TrakingDataModel> findAllTracking() {
        return iTracking.findAll();
    }

    @Override
    public Optional<TrakingDataModel> findByTrackingById(Long id) {
        return iTracking.findById(id);
    }

    @Override
    public Optional<TrakingDataModel> findByTrackingByWaybill(String waybill) {
        log.info("Numero de Tracking: {}",waybill);
        RespuestaEstafeta trackingAPI = restTemplate.getForObject("http://localhost:56098/ananda/jupiter/titan/estafeta/trackingByNumber?numeroGuia="+waybill, RespuestaEstafeta.class);
        log.info("tracking: {}",trackingAPI);
        TrakingDataModel tracking = mapearEntidadTracking(trackingAPI);
        Optional<TrakingDataModel> oTracking = null;
        if(tracking.getCodigoGuia() !=null){
            oTracking = iTracking.findByCodigoGuia(waybill);
            if(oTracking.isEmpty()){
                RespuestaEstafeta eQResult = restTemplate.getForObject(basePath+"/estafeta/trackingByNumber?numeroGuia="+waybill, RespuestaEstafeta.class);
                List<HistoriaModel> listaHistorias = new ArrayList<>();
                for(var historia:eQResult.getExecuteQueryResponse().getExecuteQueryResult().getTrackingData().getTrackingData().getHistory().getHistory()){
                    listaHistorias.add(mapearEntidadHistoria(historia));
                    for(HistoriaModel ht:listaHistorias){
                        ht.setTrackingData(tracking);
                        iHistory.save(ht);
                    }
                }
                iTracking.save(tracking);
                Optional<TrakingDataModel> trackingTraido= iTracking.findById(tracking.getIdTracking());
                return trackingTraido;
            }
            else{
                Optional<TrakingDataModel> trackingYaCreado = iTracking.findById(oTracking.get().getIdTracking());
                return trackingYaCreado;
            }
        }
        else{
            return Optional.empty();
        }

    }

    @Override
    public Optional<TrakingDataModel> findByTrackingByShortWaybillId(String shortWaybillId) {
        TrakingDataModel trackingAPI = mapearEntidadTracking(restTemplate.getForObject(basePath+"/estafeta/trackingShortWayBill?numeroRastreo="+shortWaybillId, RespuestaEstafeta.class));
        Optional<TrakingDataModel> oTracking = null;
        if(trackingAPI.getCodigoRastreo() !=null){
            oTracking = iTracking.findByCodigoRastreo(shortWaybillId);
            if(oTracking.isEmpty()){
                ExecuteQueryResult eQResult = restTemplate.getForObject(basePath+"/estafeta/trackingByNumber?numeroGuia="+shortWaybillId, ExecuteQueryResult.class);
                List<HistoriaModel> listaHistorias = new ArrayList<>();
                for(var historia:eQResult.getTrackingData().getTrackingData().getHistory().getHistory()){
                    listaHistorias.add(mapearEntidadHistoria(historia));
                    for(HistoriaModel ht:listaHistorias){
                        ht.setTrackingData(trackingAPI);
                        iHistory.save(ht);
                    }
                }
                iTracking.save(trackingAPI);
                Optional<TrakingDataModel> trackingTraido= iTracking.findById(trackingAPI.getIdTracking());
                return trackingTraido;
            }
            else{
                Optional<TrakingDataModel> trackingYaCreado = iTracking.findById(oTracking.get().getIdTracking());
                return trackingYaCreado;
            }
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public List<HistoriaModel> findAllHistoryByTrackingId(Long idTracking) {
        return iHistory.findByTrackingData_IdTracking(idTracking);
    }

    private TrakingDataModel mapearEntidadTracking(RespuestaEstafeta resulstado){
        TrakingDataModel tracking = new TrakingDataModel();
        tracking.setCodigoError(resulstado.getExecuteQueryResponse().getExecuteQueryResult().getErrorCode());
        tracking.setDescripcionError(resulstado.getExecuteQueryResponse().getExecuteQueryResult().getErrorCodeDescriptionSPA());
        tracking.setEstatusTracking(resulstado.getExecuteQueryResponse().getExecuteQueryResult().getTrackingData().getTrackingData().getStatusSPA());
        tracking.setCodigoGuia(resulstado.getExecuteQueryResponse().getExecuteQueryResult().getTrackingData().getTrackingData().getWaybill());
        tracking.setCodigoRastreo(resulstado.getExecuteQueryResponse().getExecuteQueryResult().getTrackingData().getTrackingData().getShortWaybillId());
        return tracking;
    }

    private HistoriaModel mapearEntidadHistoria(HistoryEntry resultado){
        HistoriaModel historia = new HistoriaModel();
        historia.setCodigo_lugar(resultado.getEventPlaceAcronym());
        historia.setCodigoExcepcion(resultado.getExceptionCode());
        historia.setDescripcionExcepcion(resultado.getExceptionCodeDescriptionSPA());
        historia.setDescripcionEvento(resultado.getEventDescriptionSPA());
        historia.setLugarEvento(resultado.getEventPlaceName());
        historia.setFechaEvento(resultado.getEventDateTime());
        return historia;
    }
}
