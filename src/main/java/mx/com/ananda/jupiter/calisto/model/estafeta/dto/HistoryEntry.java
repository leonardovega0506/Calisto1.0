package mx.com.ananda.jupiter.calisto.model.estafeta.dto;

import lombok.Data;

@Data
public class HistoryEntry {

    private String exceptionCodeDetails;

    private String eventDateTime;

    private String eventId;

    private String eventPlaceName;

    private String eventDescriptionSPA;

    private String exceptionCodeDescriptionSPA;

    private String eventDescriptionENG;

    private String eventPlaceAcronym;

    private String exceptionCode;

    private String exceptionCodeDescriptionENG;
}
