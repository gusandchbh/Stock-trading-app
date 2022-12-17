package com.bonqa.bonqa.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(schema = "transaction_type")
@NoArgsConstructor
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;
}
