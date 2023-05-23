package mx.com.ananda.jupiter.calisto.model.estafeta.dto;

import lombok.Data;

@Data
public class ExecuteQueryResult {
    private String errorCodeDescriptionSPA;

    private String errorCode;

    private String errorCodeDescriptionENG;

    private TrackingData trackingData;
}
