package bonqa.marketstock.updater;

import bonqa.marketstock.MarketStock;
import bonqa.marketstock.MarketStockRepository;
import bonqa.marketstock.fetcher.MarketStockFetcherInterface;
import bonqa.marketstock.generic.StockGeneric;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MarketStockUpdater {

    private final MarketStockFetcherInterface stockFetcher;
    private final MarketStockRepository marketStockRepository;

    private final ThreadPoolTaskScheduler taskScheduler;

    private static final int API_CALL_INTERVAL_MS = 65000;

    private static final int NUMBER_OF_REQUESTS_NEEDED = 5;

    @Autowired
    public MarketStockUpdater(@Qualifier("alphaVantageMarketStockFetcher")MarketStockFetcherInterface stockFetcher, MarketStockRepository marketStockRepository, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.stockFetcher = stockFetcher;
        this.marketStockRepository = marketStockRepository;
        this.taskScheduler = threadPoolTaskScheduler;
    }

    @PostConstruct
    public void scheduleFetchTasks() {
        Runnable fetchTask = this::update;
        for (int i = 0; i < NUMBER_OF_REQUESTS_NEEDED; i++) {
            Instant nextRunInstant = Instant.now().plusMillis(i * (long) API_CALL_INTERVAL_MS);
            taskScheduler.schedule(fetchTask, nextRunInstant);
        }
    }



    @Scheduled(cron = "0 0 22 * * ?") // At 22:00:00pm every day
    private void automaticStockUpdate() {
        this.scheduleFetchTasks();
    }

    public void update() {
        List<StockGeneric> stocks = stockFetcher.fetchStocksFromAPI();
        this.updateStocksInDatabase(stocks);
    }

    private void updateStocksInDatabase(List<StockGeneric> stocks) {
        for (var stock : stocks) {
            MarketStock marketStockToSave = marketStockRepository.findByTicker(stock.getTicker())
                .orElse(new MarketStock());

            marketStockToSave.setTicker(stock.getTicker());
            marketStockToSave.setName(stock.getName());
            marketStockToSave.setPrice(stock.getPrice());
            marketStockToSave.setVolume(stock.getVolume());
            marketStockToSave.setLastUpdated(LocalDateTime.now());

            marketStockRepository.save(marketStockToSave);
        }
    }
}
