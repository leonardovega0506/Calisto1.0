package mx.com.ananda.jupiter.calisto.repository;

import mx.com.ananda.jupiter.calisto.model.estafeta.entity.HistoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistoryRepository extends JpaRepository<HistoriaModel,Long> {
    List<HistoriaModel> findByTrackingData_IdTracking(Long idTracking);
}
