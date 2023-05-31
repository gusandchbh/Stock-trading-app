package bonqa.portfolio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
    Optional<Portfolio> findByUserId(Long userId);
}
