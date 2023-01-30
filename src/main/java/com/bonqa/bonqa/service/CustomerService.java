package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.Customer;
import com.bonqa.bonqa.model.User;

public interface CustomerService {

    Customer createCustomer(User user);

    void saveCustomer(Customer customer);
}
