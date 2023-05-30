package bonqa.portfoliostock;

import bonqa.portfolio.Portfolio;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioStockRepository extends CrudRepository<PortfolioStock, Long> {
    List<PortfolioStock> findByPortfolio(Portfolio portfolio);

    List<PortfolioStock> findAll(Example<PortfolioStock> of);
}
