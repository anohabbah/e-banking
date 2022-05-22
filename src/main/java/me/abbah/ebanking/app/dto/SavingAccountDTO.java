package me.abbah.ebanking.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.abbah.ebanking.app.enums.AccountStatus;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true)
@Data
public class SavingAccountDTO extends AccountDTO {
    private UUID id;
    private Long balance;
    private Date createdAt;
    private AccountStatus status;
    private double interestRate;
    private CustomerDTO customer;
}
