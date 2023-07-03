package bonqa.marketstock.fetcher;

import bonqa.marketstock.MarketStock;
import bonqa.marketstock.MarketStockRepository;
import bonqa.marketstock.generic.StockGeneric;
import bonqa.marketstock.generic.StockGenericFactory;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlphaVantageMarketStockFetcher implements MarketStockFetcherInterface {
    private final StockGenericFactory stockGenericFactory;
    private final MarketStockRepository marketStockRepository;
    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageMarketStockFetcher.class);

    private static final Map<String, String> TICKERS = createTickerMap();

    @Autowired
    public AlphaVantageMarketStockFetcher(
            StockGenericFactory stockGenericFactory, MarketStockRepository marketStockRepository) {
        this.stockGenericFactory = stockGenericFactory;
        this.marketStockRepository = marketStockRepository;
    }

    @PostConstruct
    public void initAlphaVantageAPI() {
        Config cfg = Config.builder().key("ZRCOXVS4JDB67YPW").timeOut(10).build();

        AlphaVantage.api().init(cfg);
        logger.info("AlphaVantage API Initialized successfully.");
    }

    @Override
    public List<StockGeneric> fetchStocksFromAPI() {
        List<StockGeneric> result = new ArrayList<>();
        Instant now = Instant.now();
        Instant twentyFourHoursAgo = now.minus(Duration.ofHours(24));

        for (Map.Entry<String, String> entry : TICKERS.entrySet()) {
            String ticker = entry.getKey();
            String name = entry.getValue();

            Optional<MarketStock> optionalStock = marketStockRepository.findByTicker(ticker);
            if (optionalStock.isPresent()) {
                var existingStock = optionalStock.get();
                ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
                if (existingStock.getLastUpdated().toInstant(zoneOffset).isAfter(twentyFourHoursAgo)) {
                    continue;
                }
            }

            logger.info("Fetching data for {}", ticker);
            TimeSeriesResponse response = AlphaVantage.api()
                    .timeSeries()
                    .intraday()
                    .forSymbol(ticker)
                    .outputSize(OutputSize.FULL)
                    .fetchSync();

            if (response.getErrorMessage() != null) {
                break;
            }

            if (!response.getStockUnits().isEmpty()) {
                result.add(stockGenericFactory.createFromAlphaVantageStock(
                        response.getStockUnits().get(0), name, ticker));
            } else {
                logger.error("No stock units returned for {}", ticker);
            }
        }
        return result;
    }

    private static Map<String, String> createTickerMap() {
        return Map.ofEntries(
                Map.entry("AAPL", "Apple Inc."),
                Map.entry("MSFT", "Microsoft Corporation"),
                Map.entry("GOOG", "Alphabet Inc."),
                Map.entry("AMZN", "Amazon.com Inc."),
                Map.entry("NVDA", "NVIDIA Corporation"),
                Map.entry("XOM", "Exxon Mobil Corporation"),
                Map.entry("TSLA", "Tesla Inc."),
                Map.entry("JPM", "JPMorgan Chase & Co."),
                Map.entry("WMT", "Walmart Inc."),
                Map.entry("META", "Meta Platforms Inc."), // Formerly Facebook
                Map.entry("BAC", "Bank of America Corp."),
                Map.entry("KO", "The Coca-Cola Co."),
                Map.entry("PFE", "Pfizer Inc."),
                Map.entry("MCD", "McDonald's Corp."),
                Map.entry("CSCO", "Cisco Systems Inc."),
                Map.entry("DIS", "The Walt Disney Company"),
                Map.entry("NFLX", "Netflix Inc."),
                Map.entry("T", "AT&T Inc."),
                Map.entry("BA", "The Boeing Company"),
                Map.entry("INTC", "Intel Corporation"),
                Map.entry("AMD", "Advanced Micro Devices Inc."),
                Map.entry("GE", "General Electric Company"),
                Map.entry("F", "Ford Motor Company"));
    }
}
