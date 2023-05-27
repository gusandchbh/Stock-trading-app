package bonqa.marketstock.fetcher;

import bonqa.marketstock.generic.StockGeneric;
import bonqa.marketstock.generic.StockGenericFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

@Service
public class YahooMarketStockFetcher implements MarketStockFetcherInterface {
    private final StockGenericFactory stockGenericFactory;

    @Autowired
    public YahooMarketStockFetcher(StockGenericFactory stockGenericFactory) {
        this.stockGenericFactory = stockGenericFactory;
    }

    @Override
    public List<StockGeneric> fetchStocksFromAPI() {
        try {
            String[] arr = {
                "AAPL", "MSFT", "GOOG", "AMZN", "NVDA", "XOM", "TSLA", "JPM", "WMT", "META", "BAC", "KO", "PFE", "MCD",
                "CSCO", "DIS", "NFLX", "T", "BA", "INTC", "AMD", "GE", "F"
            };
            // https://github.com/sstrickx/yahoofinance-api/issues/206
            System.setProperty(
                    "yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v6/finance/quote");
            return this.stockGenericFactory.createFromYahooStocks(YahooFinance.get(arr));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>();
    }
}
