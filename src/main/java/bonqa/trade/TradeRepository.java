package bonqa.trade;

import bonqa.portfolio.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT t FROM Trade t WHERE t.portfolio.id = :portfolioId AND t.marketStock.id = :stockId")
    List<Trade> findByPortfolioIdAndStockId(Long portfolioId, Long stockId);

    Page<Trade> findAll(Specification<Trade> spec, Pageable pageable);
}
