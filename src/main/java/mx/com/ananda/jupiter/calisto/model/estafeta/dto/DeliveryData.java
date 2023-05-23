package mx.com.ananda.jupiter.calisto.model.estafeta.dto;

import lombok.Data;

@Data
public class DeliveryData {

    private String destinationAcronym;

    private String zipCode;

    private String deliveryDateTime;

    private String receiverName;

    private String destinationName;
}
