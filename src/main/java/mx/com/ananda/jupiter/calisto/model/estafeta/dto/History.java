package mx.com.ananda.jupiter.calisto.model.estafeta.dto;

import lombok.Data;

import java.util.List;

@Data
public class History {
    private List<HistoryEntry> entries;
}
