package mx.com.ananda.jupiter.calisto.services.implementation;

import lombok.Data;
import mx.com.ananda.jupiter.calisto.model.estafeta.dto.ExecuteQueryResult;
import mx.com.ananda.jupiter.calisto.model.estafeta.dto.HistoryEntry;
import mx.com.ananda.jupiter.calisto.model.estafeta.dto.TrackingData;
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
        TrakingDataModel trackingAPI = mapearEntidadTracking(restTemplate.getForObject(basePath+"/estafeta/trackingByNumber?numeroGuia="+waybill, ExecuteQueryResult.class));
        Optional<TrakingDataModel> oTracking = null;
        if(trackingAPI.getCodigoGuia() !=null){
            oTracking = iTracking.findByCodigoGuia(waybill);
            if(oTracking.isEmpty()){
                ExecuteQueryResult eQResult = restTemplate.getForObject(basePath+"/estafeta/trackingByNumber?numeroGuia="+waybill, ExecuteQueryResult.class);
                List<HistoriaModel> listaHistorias = new ArrayList<>();
                for(var historia:eQResult.getTrackingData().getTrackingDataDetails().getHistory().getEntries()){
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
    public Optional<TrakingDataModel> findByTrackingByShortWaybillId(String shortWaybillId) {
        TrakingDataModel trackingAPI = mapearEntidadTracking(restTemplate.getForObject(basePath+"/estafeta/trackingByNumber?numeroGuia="+shortWaybillId, ExecuteQueryResult.class));
        Optional<TrakingDataModel> oTracking = null;
        if(trackingAPI.getCodigoGuia() !=null){
            oTracking = iTracking.findByCodigoRastreo(shortWaybillId);
            if(oTracking.isEmpty()){
                ExecuteQueryResult eQResult = restTemplate.getForObject(basePath+"/estafeta/trackingByNumber?numeroGuia="+shortWaybillId, ExecuteQueryResult.class);
                List<HistoriaModel> listaHistorias = new ArrayList<>();
                for(var historia:eQResult.getTrackingData().getTrackingDataDetails().getHistory().getEntries()){
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

    private TrakingDataModel mapearEntidadTracking(ExecuteQueryResult resulstado){
        TrakingDataModel tracking = new TrakingDataModel();
        tracking.setCodigoError(resulstado.getErrorCode());
        tracking.setDescripcionError(resulstado.getErrorCodeDescriptionSPA());
        tracking.setEstatusTracking(resulstado.getTrackingData().getTrackingDataDetails().getStatusSPA());
        tracking.setCodigoGuia(resulstado.getTrackingData().getTrackingDataDetails().getWaybill());
        tracking.setCodigoRastreo(resulstado.getTrackingData().getTrackingDataDetails().getShortWaybillId());
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
