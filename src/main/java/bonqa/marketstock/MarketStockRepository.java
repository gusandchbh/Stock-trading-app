package bonqa.marketstock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketStockRepository extends CrudRepository<MarketStock, Long> {}
