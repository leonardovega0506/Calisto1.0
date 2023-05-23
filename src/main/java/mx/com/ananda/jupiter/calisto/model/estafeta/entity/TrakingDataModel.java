package mx.com.ananda.jupiter.calisto.model.estafeta.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "tracking")
@Entity
@Data
public class TrakingDataModel {

    @Id
    @GeneratedValue
    @Column(name = "id_tracking")
    private Long idTracking;

    @Column(name = "codigo_error")
    private String codigoError;

    @Column(name = "descripcion_error")
    private String descripcionError;

    @Column(name = "estatus_tracking")
    private String estatusTracking;

    @Column(name = "codigo_rastreo")
    private String codigoRastreo;

    @Column(name = "codigo_guia")
    private String codigoGuia;

    @OneToMany(mappedBy = "trackingData")
    private List<HistoriaModel> historias;

}
