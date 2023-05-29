package bonqa.portfoliostock;

import bonqa.marketstock.MarketStock;
import bonqa.marketstock.MarketStockRepository;
import bonqa.portfolio.Portfolio;
import bonqa.portfolio.PortfolioRepository;
import bonqa.portfolio.PortfolioService;
import bonqa.portfoliostock.exception.InsufficientFundsException;
import bonqa.portfoliostock.exception.ResourceNotFoundException;
import bonqa.trade.Trade;
import bonqa.trade.TradeFactory;
import bonqa.trade.TradeRepository;
import bonqa.trade.TradeType;
import bonqa.user.User;
import bonqa.user.UserRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioStockService {

    private final UserRepository userRepository;
    private final MarketStockRepository marketStockRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioService portfolioService;

    private final TradeRepository tradeRepository;

    private final TradeFactory tradeFactory;

    public PortfolioStockService(
        MarketStockRepository marketStockRepository,
        PortfolioRepository portfolioRepository,
        UserRepository userRepository,
        PortfolioService portfolioService,
        TradeRepository tradeRepository,
        TradeFactory tradeFactory) {
        this.userRepository = userRepository;
        this.marketStockRepository = marketStockRepository;
        this.portfolioRepository = portfolioRepository;
        this.portfolioService = portfolioService;
        this.tradeRepository = tradeRepository;
        this.tradeFactory = tradeFactory;
    }

    public MarketStock findStockById(Long stockId) throws ResourceNotFoundException {
        return marketStockRepository
                .findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }

    public List<PortfolioStock> getAllUserStocks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Portfolio portfolio = user.getPortfolio();
        return portfolio.getStocks();
    }

    @Transactional
    public String purchaseStock(Long userId, Long stockId, int quantity) {
        User user = findUserById(userId);
        Portfolio portfolio = user.getPortfolio();
        MarketStock marketStock = findStockById(stockId);

        BigDecimal purchasePricePerShare = marketStock.getPrice();
        BigDecimal totalPrice = calculateAmount(purchasePricePerShare, quantity);

        checkSufficientFunds(portfolio, totalPrice);

        PortfolioStock portfolioStock =
                updatePortfolioAndPurchaseTrade(portfolio, marketStock, quantity, purchasePricePerShare, totalPrice);

        var pricePerShare = portfolioStock.getMarketStock().getPrice();

        return generateTransactionMessage(portfolioStock, quantity, pricePerShare, TradeType.BUY);
    }

    private void checkSufficientFunds(Portfolio portfolio, BigDecimal totalPrice) {
        if (portfolio.getAccountBalance().compareTo(totalPrice) < 0) {
            throw new InsufficientFundsException("Insufficient funds to purchase the stock");
        }
    }

    private PortfolioStock updatePortfolioAndPurchaseTrade(
        Portfolio portfolio,
        MarketStock marketStock,
        int quantity,
        BigDecimal purchasePricePerShare,
        BigDecimal totalPrice) {
        portfolio.setAccountBalance(portfolio.getAccountBalance().subtract(totalPrice));

        PortfolioStock portfolioStock = findOrCreatePortfolioStock(portfolio, marketStock);

        updatePortfolioStockForPurchase(portfolioStock, quantity, purchasePricePerShare);

        Trade trade = tradeFactory.createTrade(totalPrice, quantity, purchasePricePerShare, TradeType.BUY, portfolio, marketStock);
        tradeRepository.save(trade);

        portfolioRepository.save(portfolio);

        portfolioService.updateTotalValue(portfolio);
        return portfolioStock;
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
                    newPortfolioStock.setQuantity(0); // Initialize the quantity to 0
                    newPortfolioStock.setAveragePurchasePrice(
                            BigDecimal.ZERO); // Initialize the averagePurchasePrice to 0
                    portfolio
                            .getStocks()
                            .add(newPortfolioStock); // Add the new PortfolioStock to the Portfolio's stocks list
                    return newPortfolioStock;
                });
    }

    private void updatePortfolioStockForPurchase(
            PortfolioStock portfolioStock, int quantity, BigDecimal purchasePricePerShare) {
        if (portfolioStock.getQuantity() == null) {
            initializeNewPortfolioStock(portfolioStock, quantity, purchasePricePerShare);
        } else {
            updateExistingPortfolioStock(portfolioStock, quantity, purchasePricePerShare);
        }
    }

    private void initializeNewPortfolioStock(
            PortfolioStock portfolioStock, int quantity, BigDecimal purchasePricePerShare) {
        portfolioStock.setAveragePurchasePrice(purchasePricePerShare);
        portfolioStock.setQuantity(quantity);

        BigDecimal bigDecimalQuantity = BigDecimal.valueOf(portfolioStock.getQuantity());
        BigDecimal currentValue = bigDecimalQuantity.multiply(purchasePricePerShare);
        portfolioStock.setTotalValue(currentValue);
    }

    private void updateExistingPortfolioStock(
            PortfolioStock portfolioStock, int quantity, BigDecimal purchasePricePerShare) {
        int oldQuantity = portfolioStock.getQuantity();
        BigDecimal oldAveragePurchasePrice = portfolioStock.getAveragePurchasePrice();

        int newQuantity = oldQuantity + quantity;
        BigDecimal newAveragePurchasePrice = (oldAveragePurchasePrice
                        .multiply(BigDecimal.valueOf(oldQuantity))
                        .add(purchasePricePerShare.multiply(BigDecimal.valueOf(quantity))))
                .divide(BigDecimal.valueOf(newQuantity), 2, RoundingMode.HALF_UP);

        portfolioStock.setQuantity(newQuantity);
        portfolioStock.setAveragePurchasePrice(newAveragePurchasePrice);

        BigDecimal bigDecimalQuantity = BigDecimal.valueOf(portfolioStock.getQuantity());
        BigDecimal currentValue =
                bigDecimalQuantity.multiply(portfolioStock.getMarketStock().getPrice());
        portfolioStock.setTotalValue(currentValue);
    }

    @Transactional
    public String sellStock(Long userId, Long stockId, int quantity) {
        User user = findUserById(userId);
        Portfolio portfolio = user.getPortfolio();
        MarketStock marketStock = findStockById(stockId);
        PortfolioStock portfolioStock = findPortfolioStock(portfolio, stockId);

        checkSufficientShares(quantity, portfolioStock);

        BigDecimal sellPricePerShare = marketStock.getPrice();
        BigDecimal amount = calculateAmount(sellPricePerShare, quantity);

        updatePortfolioAndTrade(portfolio, marketStock, quantity, sellPricePerShare, amount, TradeType.SELL);

        return generateTransactionMessage(portfolioStock, quantity, sellPricePerShare, TradeType.SELL);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private PortfolioStock findPortfolioStock(Portfolio portfolio, Long stockId) {
        return portfolio.getStocks().stream()
                .filter(ps -> ps.getMarketStock().getId().equals(stockId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found in the portfolio"));
    }

    private void checkSufficientShares(int quantity, PortfolioStock portfolioStock) {
        if (quantity > portfolioStock.getQuantity()) {
            throw new PortfolioStock.InsufficientSharesException("Insufficient quantity to sell the stock");
        }
    }

    private BigDecimal calculateAmount(BigDecimal sellPricePerShare, int quantity) {
        return sellPricePerShare.multiply(BigDecimal.valueOf(quantity));
    }

    private void updatePortfolioAndTrade(
        Portfolio portfolio,
        MarketStock marketStock,
        int quantity,
        BigDecimal sellPricePerShare,
        BigDecimal amount,
        TradeType tradeType) {
        portfolioService.updateAccountBalance(portfolio, amount);

        Trade trade = tradeFactory.createTrade(amount, quantity, sellPricePerShare, tradeType, portfolio, marketStock);
        tradeRepository.save(trade);

        updatePortfolioStock(portfolio, marketStock, quantity);
    }

    private void updatePortfolioStock(Portfolio portfolio, MarketStock marketStock, int quantity) {
        PortfolioStock portfolioStock = findPortfolioStock(portfolio, marketStock.getId());
        int newQuantity = portfolioStock.getQuantity() - quantity;
        if (newQuantity == 0) {
            portfolioService.removePortfolioStock(portfolio.getId(), portfolioStock);
        } else {
            portfolioStock.setQuantity(newQuantity);
            portfolioService.updatePortfolioStockValue(portfolio.getId(), portfolioStock);
        }
    }

    private String generateTransactionMessage(
            PortfolioStock portfolioStock, int quantity, BigDecimal pricePerShare, TradeType tradeType) {
        String stockName = portfolioStock.getStockName();
        BigDecimal bigDecimalQuantity = BigDecimal.valueOf(quantity);
        BigDecimal totalValue = bigDecimalQuantity.multiply(pricePerShare);
        String operation = tradeType == TradeType.BUY ? "bought" : "sold";

        return String.format(
                "You have %s %d shares of %s for %.2f USD", operation, quantity, stockName, totalValue.doubleValue());
    }
}
