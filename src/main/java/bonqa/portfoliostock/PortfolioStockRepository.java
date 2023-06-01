package bonqa.portfoliostock;

import bonqa.portfolio.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioStockRepository extends CrudRepository<PortfolioStock, Long> {
    List<PortfolioStock> findByPortfolio(Portfolio portfolio);

}
