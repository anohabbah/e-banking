package me.abbah.ebanking.app.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import me.abbah.ebanking.app.enums.AccountStatus;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
@DiscriminatorColumn(name = "type", length = 4)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long balance;

    @CreatedDate
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ToString.Exclude
    @ManyToOne
    private Customer customer;

    @ToString.Exclude
    @OneToMany(mappedBy = "account")
    private List<AccountOperation> operations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
