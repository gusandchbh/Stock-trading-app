package bonqa.domain.repository;

import bonqa.domain.model.Portfolio;
import bonqa.domain.model.PortfolioStock;
import bonqa.domain.model.Stock;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioStockRepository extends CrudRepository<PortfolioStock, Long> {

  Optional<PortfolioStock> findByPortfolioAndStock(Portfolio portfolio, Stock stock);
}
