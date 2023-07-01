package bonqa.portfoliostock;

import bonqa.marketstock.MarketStock;
import bonqa.marketstock.MarketStockRepository;
import bonqa.portfolio.Portfolio;
import bonqa.portfolio.PortfolioRepository;
import bonqa.portfolio.PortfolioService;
import bonqa.portfoliostock.exception.ResourceNotFoundException;
import bonqa.trade.Trade;
import bonqa.trade.TradeRepository;
import bonqa.trade.TradeType;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioStockService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioStockController.class);

    private final PortfolioStockRepository portfolioStockRepository;
    private final MarketStockRepository marketStockRepository;
    private final PortfolioService portfolioService;
    private final TradeRepository tradeRepository;

    @Autowired
    public PortfolioStockService(PortfolioStockRepository portfolioStockRepository,
                                 MarketStockRepository marketStockRepository,
                                 PortfolioService portfolioService,
                                 TradeRepository tradeRepository) {
        this.portfolioStockRepository = portfolioStockRepository;
        this.marketStockRepository = marketStockRepository;
        this.portfolioService = portfolioService;
        this.tradeRepository = tradeRepository;
    }

    public String purchaseStock(Long userId, Long stockId, int quantity) {
        MarketStock marketStock = marketStockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);

        BigDecimal requiredAmount = marketStock.getPrice().multiply(BigDecimal.valueOf(quantity));
        if (portfolio.getAccountBalance().compareTo(requiredAmount) < 0) {
            return "Not enough balance to purchase stocks";
        }

        createTrade(portfolio, marketStock, quantity, TradeType.BUY);

        PortfolioStock portfolioStock = findOrCreatePortfolioStock(portfolio, marketStock);

        portfolioStock.setQuantity(portfolioStock.getQuantity() + quantity);
        portfolioService.updatePortfolioStockValue(portfolio.getId(), portfolioStock);

        portfolioService.updateAccountBalance(portfolio, requiredAmount.negate());

        return "Stock purchase successful";
    }


    public String sellStock(Long userId, Long stockId, int quantity) {
        MarketStock marketStock = marketStockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);

        PortfolioStock portfolioStock = findPortfolioStock(portfolio, stockId);

        // Force the initialization of the proxy
        portfolioStock.getPortfolio().getStocks().size();

        if (portfolioStock.getQuantity() < quantity) {
            return "Not enough stocks to sell";
        }

        Trade trade = createTrade(portfolio, marketStock, quantity, TradeType.SELL);

        portfolioStock.setQuantity(portfolioStock.getQuantity() - quantity);

        if(portfolioStock.getQuantity() == 0) {
            portfolioService.removePortfolioStock(portfolio.getId(), portfolioStock);
        } else {
            portfolioService.updatePortfolioStockValue(portfolio.getId(), portfolioStock);
        }

        portfolioService.updateAccountBalance(portfolio, trade.getAmount());

        return "Stock sale successful";
    }


    public List<PortfolioStock> getAllUserStocks(Long userId) {
        Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);
        return portfolioStockRepository.findByPortfolio(portfolio);
    }

    private Trade createTrade(Portfolio portfolio, MarketStock marketStock, int quantity, TradeType tradeType) {
        Trade trade = new Trade();
        trade.setPortfolio(portfolio);
        trade.setMarketStock(marketStock);
        trade.setAmount(marketStock.getPrice().multiply(BigDecimal.valueOf(quantity)));
        trade.setTradeType(tradeType);
        trade.setShares(quantity);
        trade.setCreateDate(LocalDateTime.now());
        trade.setPricePerShare(marketStock.getPrice());
        tradeRepository.save(trade);
        return trade;
    }

    private PortfolioStock findOrCreatePortfolioStock(Portfolio portfolio, MarketStock marketStock) {
        return portfolio.getStocks().stream()
                .filter(ps -> ps.getMarketStock().getId().equals(marketStock.getId()))
                .findFirst()
                .orElseGet(() -> {
                    PortfolioStock newPortfolioStock = new PortfolioStock();
                    newPortfolioStock.setPortfolio(portfolio);
                    newPortfolioStock.setMarketStock(marketStock);
                    newPortfolioStock.setStockName(marketStock.getName());
                    newPortfolioStock.setQuantity(0);
                    portfolio
                            .getStocks()
                            .add(newPortfolioStock);
                    return newPortfolioStock;
                });
    }

    private PortfolioStock findPortfolioStock(Portfolio portfolio, Long stockId) {
        return portfolio.getStocks().stream()
                .filter(ps -> ps.getMarketStock().getId().equals(stockId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found in the portfolio"));
    }

    public Optional<PortfolioStock> getPortfolioStock(Long userId, Long stockId) {
        Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);
        return portfolio.getStocks().stream()
                .filter(ps -> ps.getMarketStock().getId().equals(stockId))
                .findFirst();
    }


}
