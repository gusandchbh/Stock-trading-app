package bonqa.marketstock.generic;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StockGenericFactory {

    private StockGeneric createFromYahooStock(yahoofinance.Stock stock) {
        StockGeneric stockGeneric = new StockGeneric();
        stockGeneric.setTicker(stock.getSymbol());
        stockGeneric.setName(stock.getName());
        stockGeneric.setPrice(stock.getQuote().getPrice());
        stockGeneric.setVolume(stock.getQuote().getVolume());
        stockGeneric.setOpen(stock.getQuote().getOpen());
        stockGeneric.setClose(stock.getQuote().getPreviousClose());

        return stockGeneric;
    }

    public StockGeneric createFromAlphaVantageStock(StockUnit stockUnit, String name, String ticker) {
        StockGeneric stockGeneric = new StockGeneric();
        stockGeneric.setTicker(ticker);
        stockGeneric.setName(name);
        stockGeneric.setPrice(BigDecimal.valueOf(stockUnit.getClose()));
        stockGeneric.setVolume(stockUnit.getVolume());
        stockGeneric.setOpen(BigDecimal.valueOf(stockUnit.getOpen()));
        stockGeneric.setClose(BigDecimal.valueOf(stockUnit.getClose()));
        stockGeneric.setDate(stockUnit.getDate());

        return stockGeneric;
    }

    public List<StockGeneric> createFromYahooStocks(Map<String, yahoofinance.Stock> stocks) {
        var apiStocks = new ArrayList<StockGeneric>();

        for (yahoofinance.Stock stock : stocks.values()) {
            apiStocks.add(this.createFromYahooStock(stock));
        }
        return apiStocks;
    }
}
