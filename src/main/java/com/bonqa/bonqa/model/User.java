package com.bonqa.bonqa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "user")
@Builder
@AllArgsConstructor
public class User {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}


