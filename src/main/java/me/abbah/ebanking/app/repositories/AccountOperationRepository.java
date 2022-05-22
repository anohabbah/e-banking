package me.abbah.ebanking.app.repositories;

import me.abbah.ebanking.app.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, UUID> {
    List<AccountOperation> findByAccountId(UUID accountId);

    Page<AccountOperation> findByAccountId(UUID accountId, Pageable pageable);
}
