package bonqa.portfolio;

import bonqa.portfoliostock.PortfolioStock;
import bonqa.portfoliostock.PortfolioStockRepository;
import bonqa.trade.Trade;
import bonqa.trade.TradeRepository;
import bonqa.trade.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TradeRepository tradeRepository;

    private final PortfolioStockRepository portfolioStockRepository;

    @Autowired
    public PortfolioService(
            PortfolioRepository portfolioRepository,
            TradeRepository tradeRepository,
            PortfolioStockRepository portfolioStockRepository) {
        this.portfolioRepository = portfolioRepository;
        this.tradeRepository = tradeRepository;
        this.portfolioStockRepository = portfolioStockRepository;
    }

    public void updateTotalValue(Portfolio portfolio) {
        BigDecimal newTotalValue = portfolio.getStocks().stream()
                .map(portfolioStock -> portfolioStock
                        .getMarketStock()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(portfolioStock.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        portfolio.setTotalValue(newTotalValue);
        portfolioRepository.save(portfolio);
    }

    @Transactional
    public void removePortfolioStock(Long portfolioId, PortfolioStock portfolioStock) {
        if (portfolioStock == null || portfolioId == null) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
        if (optionalPortfolio.isPresent()) {
            Portfolio portfolio = optionalPortfolio.get();
            if (portfolio.getStocks().remove(portfolioStock)) {
                updateTotalValue(portfolio);
                portfolioRepository.save(portfolio);
                portfolioStockRepository.delete(portfolioStock);
            }
        }
    }

    @Transactional
    public void updatePortfolioStockValue(Long portfolioId, PortfolioStock portfolioStock) {
        if (portfolioStock == null || portfolioId == null) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
        if (optionalPortfolio.isPresent()) {
            Portfolio portfolio = optionalPortfolio.get();
            if (portfolio.getStocks().contains(portfolioStock)) {
                List<Trade> trades = tradeRepository.findByPortfolioIdAndStockId(
                    portfolioId, portfolioStock.getMarketStock().getId());

                TradeSummary tradeSummary = calculateTradeSummary(trades);

                BigDecimal averageValue = tradeSummary.totalShares() == 0
                    ? BigDecimal.ZERO
                    : tradeSummary.totalValue().divide(
                    BigDecimal.valueOf(tradeSummary.totalShares()), RoundingMode.HALF_UP);

                BigDecimal currentValue =
                    averageValue.multiply(BigDecimal.valueOf(portfolioStock.getQuantity()));

                portfolioStock.setTotalValue(currentValue);

                updateTotalValue(portfolio);
                portfolioRepository.save(portfolio);
            }
        }
    }

    private TradeSummary calculateTradeSummary(List<Trade> trades) {
        BigDecimal totalValue = BigDecimal.ZERO;
        int totalShares = 0;

        for (Trade trade : trades) {
            if (trade.getTradeType() == TradeType.BUY) {
                totalValue = totalValue.add(
                    trade.getPricePerShare().multiply(BigDecimal.valueOf(trade.getShares())));
                totalShares += trade.getShares();
            } else if (trade.getTradeType() == TradeType.SELL) {
                totalValue = totalValue.subtract(
                    trade.getPricePerShare().multiply(BigDecimal.valueOf(trade.getShares())));
                totalShares -= trade.getShares();
            }
        }

        return new TradeSummary(totalValue, totalShares);
    }



    public void updateAccountBalance(Portfolio portfolio, BigDecimal balanceChange) {
        BigDecimal newBalance = portfolio.getAccountBalance().add(balanceChange);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        portfolio.setAccountBalance(newBalance);
        portfolioRepository.save(portfolio);
    }

    public Portfolio getPortfolioByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User ID not found."));
    }


    public record TradeSummary(BigDecimal totalValue, int totalShares) {
    }

}
