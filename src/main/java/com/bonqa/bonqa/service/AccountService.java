package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.Account;
import com.bonqa.bonqa.model.Customer;

public interface AccountService {

    Account createAccount(Customer customer);

    void saveAccount(Account account);
}
