package mx.com.ananda.jupiter.calisto.model.dto;

import lombok.Data;

@Data
public class RespuestaEstafeta {
    private ExecuteQueryResponse executeQueryResponse;
}

public class TrackingDataDetails
{

    private DeliveryData DeliveryData;

    private MultipleServiceData MultipleServiceData;

    private String AdditionalInformation;

    private InternationalData InternationalData;

    private Signature Signature;

    private CustomerInfo CustomerInfo;

    private String ShortWaybillId;
    
    public string StatusSPA { get; set; }

            [JsonProperty("history")]
    public History History { get; set; }
}

public class DeliveryData
{
            [JsonProperty("destinationAcronym")]
    public string DestinationAcronym { get; set; }

            [JsonProperty("zipCode")]
    public string ZipCode { get; set; }

            [JsonProperty("deliveryDateTime")]
    public string DeliveryDateTime { get; set; }

            [JsonProperty("receiverName")]
    public string ReceiverName { get; set; }

            [JsonProperty("destinationName")]
    public string DestinationName { get; set; }
}

public class MultipleServiceData
{
    // Propiedades de acuerdo a la estructura de datos
}

public class InternationalData
{
    // Propiedades de acuerdo a la estructura de datos
}

public class Signature
{
    // Propiedades de acuerdo a la estructura de datos
}

public class CustomerInfo
{
    // Propiedades de acuerdo a la estructura de datos
}

public class History
{
            [JsonProperty("History")]
    public List<HistoryEntry> Entries { get; set; }
}

public class HistoryEntry
{
            [JsonProperty("exceptionCodeDetails")]
    public string ExceptionCodeDetails { get; set; }

            [JsonProperty("eventDateTime")]
    public string EventDateTime { get; set; }

            [JsonProperty("eventId")]
    public string EventId { get; set; }

            [JsonProperty("eventPlaceName")]
    public string EventPlaceName { get; set; }

            [JsonProperty("eventDescriptionSPA")]
    public string EventDescriptionSPA { get; set; }

            [JsonProperty("exceptionCodeDescriptionSPA")]
    public string ExceptionCodeDescriptionSPA { get; set; }

            [JsonProperty("eventDescriptionENG")]
    public string EventDescriptionENG { get; set; }

            [JsonProperty("eventPlaceAcronym")]
    public string EventPlaceAcronym { get; set; }

            [JsonProperty("exceptionCode")]
    public string ExceptionCode { get; set; }

            [JsonProperty("exceptionCodeDescriptionENG")]
    public string ExceptionCodeDescriptionENG { get; set; }
}
