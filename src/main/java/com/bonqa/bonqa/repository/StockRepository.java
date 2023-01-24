package com.bonqa.bonqa.repository;

import com.bonqa.bonqa.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Long> {

    Stock findOneByTicker(String ticker);
}
