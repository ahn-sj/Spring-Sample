package spring.sample.simpleinterceptor.computer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.simpleinterceptor.computer.repository.ComputerRepository;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Computer {

    @Id @GeneratedValue
    private Long id;
    private Integer price;
    private String manufacturer;
    private Integer stock;

    private LocalDateTime startedDate;
    private LocalDateTime endedDate;

    public Computer(Integer price, String manufacturer, Integer stock, LocalDateTime startedDate, LocalDateTime endedDate) {
        this.price = price;
        this.manufacturer = manufacturer;
        this.stock = stock;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
    }

    public void decreaseStock() {
        this.stock -= 1;
    }



}
