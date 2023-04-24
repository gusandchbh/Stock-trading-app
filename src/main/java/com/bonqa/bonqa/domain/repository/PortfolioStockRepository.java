package com.bonqa.bonqa.domain.repository;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.PortfolioStock;
import com.bonqa.bonqa.domain.model.Stock;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioStockRepository extends CrudRepository<PortfolioStock, Long> {

  Optional<PortfolioStock> findByPortfolioAndStock(Portfolio portfolio, Stock stock);
}
