package bonqa.portfoliostock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioStockRepository extends CrudRepository<PortfolioStock, Long> {}
