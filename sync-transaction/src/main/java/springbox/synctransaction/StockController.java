package springbox.synctransaction;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/{id}/order")
    public void order(@PathVariable Long id) {
        stockService.order(id);
    }

    @GetMapping
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @Data
    static class Request {
        private int stock;
    }
}
