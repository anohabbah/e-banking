package me.abbah.ebanking.app.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("SA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SavingAccount extends Account {
    private double interestRate;
}
