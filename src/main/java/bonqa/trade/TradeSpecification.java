package bonqa.trade;

import bonqa.marketstock.MarketStock;
import jakarta.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TradeSpecification implements Specification<Trade> {

  private final String searchTerm;

  public TradeSpecification(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public static Specification<Trade> getFilter(String searchTerm) {
    return new TradeSpecification(searchTerm);
  }

  public static Specification<Trade> getFilterByPortfolioId(Long portfolioId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("portfolio").get("id"), portfolioId);
  }

  @Override
  public Predicate toPredicate(Root<Trade> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    if (searchTerm != null) {

      predicates.add(builder.like(builder.lower(root.get("tradeType")), "%" + searchTerm.toLowerCase() + "%"));

      predicates.add(builder.like(root.get("amount").as(String.class), "%" + searchTerm + "%"));

      predicates.add(builder.like(root.get("shares").as(String.class), "%" + searchTerm + "%"));

      predicates.add(builder.like(root.get("pricePerShare").as(String.class), "%" + searchTerm + "%"));

      predicates.add(builder.like(root.get("createDate").as(String.class), "%" + searchTerm + "%"));

      Join<Trade, MarketStock> marketStockJoin = root.join("marketStock");

      predicates.add(builder.like(builder.lower(marketStockJoin.get("name")), "%" + searchTerm.toLowerCase() + "%"));
    }

    return builder.or(predicates.toArray(new Predicate[0]));
  }

}
