package bonqa.domain.stock.fetcher;

import bonqa.domain.model.data.StockGenericFactory;
import bonqa.domain.model.data.StockGeneric;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

@Service
public class YahooStockFetcher implements StockFetcherInterface {
  private final StockGenericFactory stockGenericFactory;

  @Autowired
  public YahooStockFetcher(StockGenericFactory stockGenericFactory) {
    this.stockGenericFactory = stockGenericFactory;
  }

  @Override
  public List<StockGeneric> fetchStocksFromAPI() {
    try {
      String[] arr =
          {"AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "XOM", "TSLA", "JPM", "WMT", "META", "BAC", "KO",
              "PFE", "MCD", "CSCO", "DIS", "NFLX", "T", "BA", "INTC", "AMD", "GE", "F"};

      return this.stockGenericFactory.createFromYahooStocks(YahooFinance.get(arr));
    } catch (IOException e) {
      System.out.println("Lol");
    }

    return new ArrayList<>();
  }
}
