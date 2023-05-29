package bonqa.marketstock;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketStockRepository extends CrudRepository<MarketStock, Long> {

  Optional<MarketStock> findByTicker(String ticker);
}
