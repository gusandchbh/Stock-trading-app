package com.bonqa.bonqa.repository;

import com.bonqa.bonqa.model.Portfolio;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}
