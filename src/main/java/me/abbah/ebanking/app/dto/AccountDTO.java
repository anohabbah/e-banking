package me.abbah.ebanking.app.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AccountDTO {
    private String type;
}
