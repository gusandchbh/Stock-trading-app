package com.bonqa.bonqa.domain.repository;

import com.bonqa.bonqa.domain.model.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}
