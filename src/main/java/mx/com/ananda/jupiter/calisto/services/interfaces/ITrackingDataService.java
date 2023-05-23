package mx.com.ananda.jupiter.calisto.services.interfaces;

import mx.com.ananda.jupiter.calisto.model.estafeta.entity.HistoriaModel;
import mx.com.ananda.jupiter.calisto.model.estafeta.entity.TrakingDataModel;

import java.util.List;
import java.util.Optional;

public interface ITrackingDataService {

    List<TrakingDataModel> findAllTracking();

    Optional<TrakingDataModel> findByTrackingById(Long id);

    Optional<TrakingDataModel> findByTrackingByWaybill(String waybill);

    Optional<TrakingDataModel> findByTrackingByShortWaybillId(String shortWaybillId);

    List<HistoriaModel> findAllHistoryByTrackingId(Long idTracking);
}
