package com.bonqa.bonqa.service.impl;

import com.bonqa.bonqa.model.Customer;
import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.CustomerRepository;
import com.bonqa.bonqa.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        return customer;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
