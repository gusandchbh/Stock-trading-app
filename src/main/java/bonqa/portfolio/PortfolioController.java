package bonqa.portfolio;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final ModelMapper modelMapper;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, ModelMapper modelMapper) {
        this.portfolioService = portfolioService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PortfolioDTO> getPortfolioByUserId(@PathVariable Long userId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);
            PortfolioDTO portfolioDTO = modelMapper.map(portfolio, PortfolioDTO.class);
            return new ResponseEntity<>(portfolioDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}