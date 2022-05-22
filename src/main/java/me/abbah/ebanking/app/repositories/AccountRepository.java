package me.abbah.ebanking.app.repositories;

import me.abbah.ebanking.app.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {}
