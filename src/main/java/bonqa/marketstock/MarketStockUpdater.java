package bonqa.marketstock;

import bonqa.marketstock.fetcher.MarketStockFetcherInterface;
import bonqa.marketstock.generic.StockGeneric;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketStockUpdater {


  private final MarketStockFetcherInterface stockFetcher;
  private final MarketStockRepository marketStockRepository;

  @Autowired
  public MarketStockUpdater(MarketStockFetcherInterface stockFetcher,
                            MarketStockRepository marketStockRepository) {
    this.stockFetcher = stockFetcher;
    this.marketStockRepository = marketStockRepository;
  }

  public void update() {
    List<StockGeneric> stocks = stockFetcher.fetchStocksFromAPI();
    this.updateStocksInDatabase(stocks);
  }

  @Scheduled(cron = "0 0 22 * * ?") // At 22:00:00pm every day
  private void automaticStockUpdate() {
    this.update();
  }

  private void updateStocksInDatabase(List<StockGeneric> stocks) {
    for (var stock : stocks) {
      MarketStock marketStockToSave = new MarketStock();
      marketStockToSave.setTicker(stock.getTicker());
      marketStockToSave.setName(stock.getName());
      marketStockToSave.setPrice(stock.getPrice());
      marketStockToSave.setVolume(stock.getVolume());
      marketStockToSave.setOpen(stock.getOpen());
      marketStockToSave.setClose(stock.getClose());
      marketStockRepository.save(marketStockToSave);
    }
  }
}
