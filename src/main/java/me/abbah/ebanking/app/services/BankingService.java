package me.abbah.ebanking.app.services;

import me.abbah.ebanking.app.dto.AccountDTO;
import me.abbah.ebanking.app.dto.AccountHistoryDTO;
import me.abbah.ebanking.app.dto.AccountOperationDTO;
import me.abbah.ebanking.app.dto.CurrentAccountDTO;
import me.abbah.ebanking.app.dto.CustomerDTO;
import me.abbah.ebanking.app.dto.SavingAccountDTO;
import me.abbah.ebanking.app.exceptions.AccountNotFoundException;
import me.abbah.ebanking.app.exceptions.BalanceNotSufficientException;
import me.abbah.ebanking.app.exceptions.CustomerNotFoundException;

import java.util.List;
import java.util.UUID;

public interface BankingService {
    CustomerDTO createCustomer(CustomerDTO customer);

    CurrentAccountDTO createCurrentAccount(Long initialBalance, Long overdraft, String description, UUID customerId) throws CustomerNotFoundException;

    SavingAccountDTO createSavingAccount(Long initialBalance, double interestRate, String description, UUID customerId) throws CustomerNotFoundException;

    List<CustomerDTO> getAllCustomers();

    AccountDTO getAccount(UUID accountId) throws AccountNotFoundException;

    void debit(UUID accountId, Long amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;

    void credit(UUID accountId, Long amount, String description) throws AccountNotFoundException;

    void transfert(UUID sourceAccountId, UUID destinationAccountId, Long amount) throws AccountNotFoundException, BalanceNotSufficientException;

    List<AccountDTO> getAllAccounts();

    CustomerDTO getCustomer(UUID customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO dto);

    void deleteCustomer(UUID customerId);

    List<AccountOperationDTO> getAccountHistory(UUID accountId);

    AccountHistoryDTO getAccountHistory(UUID accountId, int page, int size) throws AccountNotFoundException;
}
