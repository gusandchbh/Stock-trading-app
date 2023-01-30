package com.bonqa.bonqa.service.impl;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.bonqa.bonqa.model.Account;
import com.bonqa.bonqa.model.Customer;
import com.bonqa.bonqa.repository.AccountRepository;
import com.bonqa.bonqa.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class AccountserviceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountserviceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public Account createAccount(Customer customer) {
        Account account = new Account();
        account.setAccountNumber((long) RandomUtil.getPositiveInt());
        account.setCustomer(customer);
        account.setBalance(BigDecimal.valueOf(0));
        account.setTransactionList(new ArrayList<>());
        return account;
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
