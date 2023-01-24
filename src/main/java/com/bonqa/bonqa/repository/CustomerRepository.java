package com.bonqa.bonqa.repository;

import com.bonqa.bonqa.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
