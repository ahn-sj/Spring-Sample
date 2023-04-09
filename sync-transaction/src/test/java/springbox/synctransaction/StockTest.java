package springbox.synctransaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StockTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Test
    void singleThread() throws Exception {
        Stock stock = stockRepository.save(new Stock(1000));

        for (int i = 0; i < 1000; i++) {
            stock.decreaseStock();
        }
        assertThat(stock.getStock()).isEqualTo(0);
    }

    @Test
    void multiThread_Is_Not_Same() throws Exception {
        Stock stock = stockRepository.save(new Stock(1000));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    Stock findStock = stockRepository.findById(stock.getId()).get();
                    stockService.order(findStock.getId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        int findStock = stockRepository.findById(stock.getId()).get().getStock();
        assertThat(findStock).isNotEqualTo(0);
    }

    @Test
    void syncMultiThread_Is_Not_Same() throws Exception {
        Stock stock = stockRepository.save(new Stock(1000));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    Stock findStock = stockRepository.findById(stock.getId()).get();
                    stockService.syncOrder(findStock.getId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Stock findStock = stockRepository.findById(stock.getId()).get();
        assertThat(findStock.getStock()).isNotEqualTo(0);
    }
}
