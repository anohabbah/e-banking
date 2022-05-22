package me.abbah.ebanking.app.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
@Data
public class AccountHistoryDTO {
    private UUID accountId;
    private Long balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO> operations;
}
