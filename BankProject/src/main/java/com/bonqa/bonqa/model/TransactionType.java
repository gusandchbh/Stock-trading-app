package com.bonqa.bonqa.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(schema = "transaction_type")
@NoArgsConstructor
public class TransactionType {
    @Id
    private UUID id; // To make each transaction unique

    @Column(nullable = false)
    private String type; // Amount of the transaction
}
