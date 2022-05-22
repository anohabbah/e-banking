package me.abbah.ebanking.app.services;

import lombok.RequiredArgsConstructor;
import me.abbah.ebanking.app.dto.AccountDTO;
import me.abbah.ebanking.app.dto.AccountHistoryDTO;
import me.abbah.ebanking.app.dto.AccountOperationDTO;
import me.abbah.ebanking.app.dto.CurrentAccountDTO;
import me.abbah.ebanking.app.dto.CustomerDTO;
import me.abbah.ebanking.app.dto.SavingAccountDTO;
import me.abbah.ebanking.app.entities.Account;
import me.abbah.ebanking.app.entities.AccountOperation;
import me.abbah.ebanking.app.entities.CurrentAccount;
import me.abbah.ebanking.app.entities.Customer;
import me.abbah.ebanking.app.entities.SavingAccount;
import me.abbah.ebanking.app.enums.AccountStatus;
import me.abbah.ebanking.app.enums.OperationType;
import me.abbah.ebanking.app.exceptions.AccountNotFoundException;
import me.abbah.ebanking.app.exceptions.BalanceNotSufficientException;
import me.abbah.ebanking.app.exceptions.CustomerNotFoundException;
import me.abbah.ebanking.app.mappers.AccountMapper;
import me.abbah.ebanking.app.mappers.AccountOperationMapper;
import me.abbah.ebanking.app.mappers.CustomerMapper;
import me.abbah.ebanking.app.repositories.AccountOperationRepository;
import me.abbah.ebanking.app.repositories.AccountRepository;
import me.abbah.ebanking.app.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingServiceImpl implements BankingService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountOperationRepository operationRepository;
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;
    private final AccountOperationMapper operationMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public CurrentAccountDTO createCurrentAccount(Long initialBalance, Long overdraft, String description, UUID customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
        Account account = new CurrentAccount().overdraft(overdraft)
                .status(AccountStatus.CREATED)
                .balance(initialBalance)
                .customer(customer)
                .createdAt(new Date());
        account = accountRepository.save(account);
        return accountMapper.toCurrentDto((CurrentAccount) account);
    }

    @Override
    public SavingAccountDTO createSavingAccount(Long initialBalance,
                                                double interestRate,
                                                String description, UUID customerId)
            throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
        Account account = new SavingAccount().interestRate(interestRate)
                .status(AccountStatus.CREATED)
                .balance(initialBalance)
                .customer(customer)
                .createdAt(new Date());
        account = accountRepository.save(account);
        return accountMapper.toSavingDto((SavingAccount) account);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public AccountDTO getAccount(UUID accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        if (account instanceof SavingAccount) {
            return accountMapper.toSavingDto((SavingAccount) account);
        }

        return accountMapper.toCurrentDto((CurrentAccount) account);
    }

    @Override
    public void debit(UUID accountId, Long amount, String description) throws AccountNotFoundException,
            BalanceNotSufficientException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        if (account.balance() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient.");
        }

        AccountOperation operation = new AccountOperation().type(OperationType.DEBIT)
                .date(new Date())
                .amount(amount)
                .description(description)
                .account(account);
        operationRepository.save(operation);

        account.balance(account.balance() - amount);
        accountRepository.save(account);
    }

    @Override
    public void credit(UUID accountId, Long amount, String description) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        AccountOperation operation = new AccountOperation().type(OperationType.CREDIT)
                .date(new Date())
                .amount(amount)
                .description(description)
                .account(account);
        operationRepository.save(operation);

        account.balance(account.balance() + amount);
        accountRepository.save(account);

    }

    @Override
    public void transfert(UUID sourceAccountId, UUID destinationAccountId, Long amount) throws AccountNotFoundException, BalanceNotSufficientException {
        debit(sourceAccountId, amount, "Transfer to " + destinationAccountId);
        credit(destinationAccountId, amount, "Transfer from " + sourceAccountId);

    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> {
                    if (account instanceof SavingAccount) {
                        return accountMapper.toSavingDto((SavingAccount) account);
                    }

                    return accountMapper.toCurrentDto((CurrentAccount) account);
                })
                .toList();
    }

    @Override
    public CustomerDTO getCustomer(UUID customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> getAccountHistory(UUID accountId) {
        return operationRepository.findByAccountId(accountId)
                .stream().map(operationMapper::toDto)
                .toList();
    }

    @Override
    public AccountHistoryDTO getAccountHistory(UUID accountId, int page, int size) throws AccountNotFoundException {
        Page<AccountOperation> operations = operationRepository.findByAccountId(accountId, PageRequest.of(page, size));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        return new AccountHistoryDTO().accountId(account.id())
                .balance(account.balance())
                .pageSize(size)
                .currentPage(page)
                .totalPages(operations.getTotalPages())
                .operations(operations.get().map(operationMapper::toDto).toList());
    }
}
