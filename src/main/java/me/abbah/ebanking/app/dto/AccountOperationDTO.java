package me.abbah.ebanking.app.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import me.abbah.ebanking.app.enums.OperationType;

import java.util.Date;
import java.util.UUID;

@Accessors(fluent = true)
@Data
public class AccountOperationDTO {
    private UUID id;
    private Date date;
    private Long amount;
    private String description;
    private OperationType type;
}
