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
        LocalDateTime start = LocalDateTime.of(2022, 12, 05, 4, 0);
        LocalDateTime end = LocalDateTime.of(2022, 12, 05, 6, 0);

        Computer computer = computerRepository.save(new Computer(100000, "samsung", 1000, start, end));
        log.info("computer = {}", computer);
    }
}
