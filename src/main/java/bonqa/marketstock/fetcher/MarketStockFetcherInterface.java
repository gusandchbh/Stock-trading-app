package bonqa.marketstock.fetcher;

import bonqa.marketstock.generic.StockGeneric;
import java.util.List;

public interface MarketStockFetcherInterface {
    List<StockGeneric> fetchStocksFromAPI();
}
