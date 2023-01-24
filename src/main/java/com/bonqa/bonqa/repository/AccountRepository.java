package com.bonqa.bonqa.repository;

import com.bonqa.bonqa.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
