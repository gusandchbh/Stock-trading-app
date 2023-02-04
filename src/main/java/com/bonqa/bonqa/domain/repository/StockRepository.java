package com.bonqa.bonqa.domain.repository;

import com.bonqa.bonqa.domain.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {
    Stock findOneByTicker(String ticker);
}
