package mx.com.ananda.jupiter.calisto.model.estafeta.dto;

import lombok.Data;

@Data
public class TrackingDataDetails {

    private DeliveryData deliveryData;

    private String additionalInformation;

    private CustomerInfo customerInfo;

    private String shortWaybillId;

    private String statusSPA;

    private History history;
}
