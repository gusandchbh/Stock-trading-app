package com.bonqa.bonqa.repository;

import com.bonqa.bonqa.model.BankUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<BankUser, Long> {

    @Query("SELECT u FROM BankUser u WHERE u.username = :username")
    Optional<BankUser> findOneByUsername(String username);

}
