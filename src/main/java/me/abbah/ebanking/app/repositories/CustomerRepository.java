package me.abbah.ebanking.app.repositories;

import me.abbah.ebanking.app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {}
