package me.abbah.ebanking.app.web;

import lombok.RequiredArgsConstructor;
import me.abbah.ebanking.app.dto.AccountDTO;
import me.abbah.ebanking.app.dto.AccountHistoryDTO;
import me.abbah.ebanking.app.dto.AccountOperationDTO;
import me.abbah.ebanking.app.dto.CurrentAccountDTO;
import me.abbah.ebanking.app.dto.SavingAccountDTO;
import me.abbah.ebanking.app.exceptions.AccountNotFoundException;
import me.abbah.ebanking.app.exceptions.CustomerNotFoundException;
import me.abbah.ebanking.app.services.BankingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    private final BankingService service;

    @PostMapping("/current")
    public AccountDTO createCurrentAccount(@RequestBody CurrentAccountDTO dto) throws CustomerNotFoundException {
        return service.createCurrentAccount(dto.balance(), dto.overdraft(),
                "Initial balance", dto.customer().id());
    }

    @PostMapping("/saving")
    public AccountDTO createSavingAccount(@RequestBody SavingAccountDTO dto) throws CustomerNotFoundException {
        return service.createSavingAccount(dto.balance(), dto.interestRate(),
                "Initial balance", dto.customer().id());
    }

    @GetMapping
    public List<AccountDTO> getAll() {
        return service.getAllAccounts();
    }

    @GetMapping("/{account}")
    public AccountDTO get(@PathVariable("account") UUID accountId) throws AccountNotFoundException {
        return service.getAccount(accountId);
    }

    @GetMapping("/{account}/operations")
    public List<AccountOperationDTO> getOperations(@PathVariable("account") UUID accountId) {
        return service.getAccountHistory(accountId);
    }

    @GetMapping("/{account}/history")
    public AccountHistoryDTO getHistory(@PathVariable("account") UUID accountId,
                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "0") int size)
            throws AccountNotFoundException {
        return service.getAccountHistory(accountId, page, size);
    }
}
