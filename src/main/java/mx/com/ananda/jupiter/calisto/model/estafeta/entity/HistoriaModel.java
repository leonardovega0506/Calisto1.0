package mx.com.ananda.jupiter.calisto.model.estafeta.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "historia_registros")
public class HistoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historia")
    private Long idHistoria;

    @Column(name = "fecha_evento")
    private String fechaEvento;

    @Column(name = "lugar_evento")
    private String lugarEvento;

    @Column(name = "descripcion_evento")
    private String descripcionEvento;

    @Column(name = "codigo_lugar")
    private String codigo_lugar;

    @Column(name = "codigo_excepcion")
    private String codigoException;

    @Column(name = "descripcion_excepcion")
    private String codigoExcepcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private TrakingDataModel trackingData;
}
