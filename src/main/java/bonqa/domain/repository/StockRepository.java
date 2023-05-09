package bonqa.domain.repository;

import bonqa.domain.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
  Stock findOneByTicker(String ticker);
}
