package mx.com.ananda.jupiter.calisto.repository;

import mx.com.ananda.jupiter.calisto.model.estafeta.entity.TrakingDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITrackingRepository extends JpaRepository<TrakingDataModel, Long> {
    Optional<TrakingDataModel> findByCodigoRastreo(String codigoRastreo);
    Optional<TrakingDataModel> findByCodigoGuia(String codigoGuia);
}
