package bonqa.portfoliostock;

import bonqa.portfolio.Portfolio;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioStockRepository extends CrudRepository<PortfolioStock, Long> {
    List<PortfolioStock> findByPortfolio(Portfolio portfolio);
}
