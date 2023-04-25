package springbox.exceptionhandle.recover;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import springbox.exceptionhandle.recover.printer.Printer;
import springbox.exceptionhandle.recover.printer.PrinterRepository;
import springbox.exceptionhandle.recover.printer.PrinterService;

import javax.persistence.EntityManager;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PrinterServiceTest {

    @Autowired
    PrinterService printerService;

    @Autowired
    PrinterRepository printerRepository;

    @Test
    void printTest() throws InterruptedException {
        Printer printer = printerService.save(100);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                try {
                    printerService.print(printer.getId());
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                }
                latch.countDown();
            });
        }
        latch.await();

        Printer savePrinter = printerRepository.findById(printer.getId()).get();
        System.out.println("savePrinter = " + savePrinter.getPaper());
    }


}