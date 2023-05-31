package bonqa.marketstock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketStockRepository extends CrudRepository<MarketStock, Long> {

  Optional<MarketStock> findByTicker(String ticker);
}
