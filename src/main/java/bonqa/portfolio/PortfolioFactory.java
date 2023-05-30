package bonqa.portfolio;

import bonqa.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class PortfolioFactory {
    public Portfolio createPortfolio(User user) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setStocks(new ArrayList<>());
        portfolio.setTradeList(new ArrayList<>());
        portfolio.setTotalValue(BigDecimal.valueOf(0));

        return portfolio;
    }
}
