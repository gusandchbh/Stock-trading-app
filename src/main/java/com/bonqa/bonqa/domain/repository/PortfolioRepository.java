package com.bonqa.bonqa.domain.repository;

import com.bonqa.bonqa.domain.model.Portfolio;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}
