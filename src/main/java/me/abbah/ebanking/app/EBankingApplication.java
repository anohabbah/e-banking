package me.abbah.ebanking.app;

import me.abbah.ebanking.app.entities.CurrentAccount;
import me.abbah.ebanking.app.entities.Customer;
import me.abbah.ebanking.app.entities.SavingAccount;
import me.abbah.ebanking.app.enums.AccountStatus;
import me.abbah.ebanking.app.repositories.AccountOperationRepository;
import me.abbah.ebanking.app.repositories.AccountRepository;
import me.abbah.ebanking.app.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner seeder(CustomerRepository customerRepository,
                             AccountRepository accountRepository,
                             AccountOperationRepository operationRepository) {
        return args -> {
            Stream.of("John", "Jane").forEach((name) -> {
                Customer customer = new Customer()
                        .name(name)
                        .email(name.toLowerCase() + "@example.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = (CurrentAccount) new CurrentAccount()
                        .overdraft(new Random().nextLong())
                        .balance(new Random().nextLong())
                        .customer(customer)
                        .createdAt(new Date())
                        .status(AccountStatus.CREATED);
                accountRepository.save(currentAccount);

                SavingAccount savingAccount = (SavingAccount) new SavingAccount()
                        .interestRate(Math.abs(Math.random() * 100))
                        .balance(new Random().nextLong())
                        .customer(customer)
                        .createdAt(new Date())
                        .status(AccountStatus.CREATED);
                accountRepository.save(savingAccount);
            });
        };
    }

}
