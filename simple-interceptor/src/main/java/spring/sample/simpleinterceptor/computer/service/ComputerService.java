package spring.sample.simpleinterceptor.computer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.sample.simpleinterceptor.computer.domain.Computer;
import spring.sample.simpleinterceptor.computer.repository.ComputerRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ComputerService {

    private final ComputerRepository computerRepository;

    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @Transactional
    public Computer sellComputer(Long productId) {
        Computer findComputer = computerRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Not Exist Computer Number")
        );
        findComputer.decreaseStock();

        return findComputer;
    }

    public List<Computer> findComputers() {
        return computerRepository.findAll();
    }

    @PostConstruct
    public void init() {
        LocalDateTime start1 = LocalDateTime.of(2022, 12, 05, 4, 0);
        LocalDateTime end1 = LocalDateTime.of(2022, 12, 05, 6, 0);

        Computer computer1 = computerRepository.save(new Computer(100000, "samsung", 1000, start1, end1));
        log.info("computer1 = {}", computer1);

        LocalDateTime start2 = LocalDateTime.of(2022, 12, 20, 4, 0);
        LocalDateTime end2 = LocalDateTime.of(2022, 12, 25, 6, 0);

        Computer computer2 = computerRepository.save(new Computer(100000, "samsung", 1000, start2, end2));
        log.info("computer2 = {}", computer2);
    }
}
