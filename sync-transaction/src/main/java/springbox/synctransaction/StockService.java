package springbox.synctransaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void order(Long id) {
        Stock findStock = stockRepository.findById(id).get();
        findStock.decreaseStock();
        //stockRepository.save(findStock);
    }

    @Transactional
    public synchronized void syncOrder(Long id) {
        Stock findStock = stockRepository.findById(id).orElseThrow();
        findStock.decreaseStock();
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }
}
