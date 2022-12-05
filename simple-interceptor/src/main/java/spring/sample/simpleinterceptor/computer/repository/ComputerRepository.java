package spring.sample.simpleinterceptor.computer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.sample.simpleinterceptor.computer.domain.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
}
