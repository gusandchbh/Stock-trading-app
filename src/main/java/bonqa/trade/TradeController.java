package bonqa.trade;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

  private final ModelMapper modelMapper;
  private final TradeRepository tradeRepository;

  public TradeController(ModelMapper modelMapper, TradeRepository tradeRepository) {
    this.modelMapper = modelMapper;
    this.tradeRepository = tradeRepository;
  }


  @GetMapping("/{portfolioId}")
  public Page<TradeDTO> getTradesBySearchTerm(
      @RequestParam(required = false) String searchTerm,
      @PathVariable Long portfolioId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
    Specification<Trade> spec = Specification.where(TradeSpecification.getFilter(searchTerm))
        .and(TradeSpecification.getFilterByPortfolioId(portfolioId));
    Page<Trade> trades = tradeRepository.findAll(spec, pageable);
    return trades.map(this::convertToDto);
  }




  private TradeDTO convertToDto(Trade trade) {
    return modelMapper.map(trade, TradeDTO.class);
  }
}
