package mx.com.ananda.jupiter.calisto.model.dto;

import lombok.Data;

@Data
public class ExecuteQueryResult {
    private String errorCodeDescriptionSPA;

    private String errorCode;

    private String ErrorCodeDescriptionENG;

    private TrackingData TrackingData;
}
