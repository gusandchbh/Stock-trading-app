package com.bonqa.bonqa.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "customer")
@NoArgsConstructor
@Getter
public class Customer {
    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name ="birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name ="create_date", nullable = false)
    private LocalDate createDate;

    @Column(nullable = false)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy="customer")
    private List<Account> accountList;

    public enum Gender {
        MALE(1), FEMALE(2);

        private final int code;

        Gender(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public String getPassword() {
        return getUser().getPassword();
    }
}


