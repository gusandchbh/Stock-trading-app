package bonqa.domain.stock.fetcher;

import bonqa.domain.model.data.StockGeneric;
import java.util.List;

public interface StockFetcherInterface {
  List<StockGeneric> fetchStocksFromAPI();
}

