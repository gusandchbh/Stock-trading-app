package bonqa.portfolio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}