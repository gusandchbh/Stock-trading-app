package bonqa.domain.portfolio;

import bonqa.domain.model.Portfolio;
import bonqa.domain.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

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