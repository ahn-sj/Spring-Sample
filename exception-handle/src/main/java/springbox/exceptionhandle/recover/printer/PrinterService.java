package springbox.exceptionhandle.recover;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrinterService {

    private final PrinterRepository printerRepository;

    public PrinterService(PrinterRepository printerRepository) {
        this.printerRepository = printerRepository;
    }

    @Transactional
    @Retryable(
            value = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public Printer print(Long id) {
        Printer printer = findPrinterWithOptimisticLock(id);
        printer.decreasePaper();

        callSleep();

        return printer;
    }

    private static void callSleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Printer findPrinterWithOptimisticLock(Long id) {
        return printerRepository.findByIdWithLock(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 프린터기를 찾을 수 없습니다.");
        });
    }

    @Transactional
    public Printer save(int paper) {
        return printerRepository.save(new Printer(paper));
    }
}
