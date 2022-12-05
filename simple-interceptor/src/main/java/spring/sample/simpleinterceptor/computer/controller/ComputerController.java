package spring.sample.simpleinterceptor.computer.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.sample.simpleinterceptor.computer.domain.Computer;
import spring.sample.simpleinterceptor.computer.service.ComputerService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ComputerController {

    private final ComputerService computerService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping("/api/v1")
    public List<ResponseDto> getComputer() {
        List<Computer> computers = computerService.findComputers();

        return computers.stream()
                .map(computer -> new ResponseDto(computer))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/{productId}/order")
    public ResponseDto postComputer(@PathVariable Long productId) {
        return new ResponseDto(computerService.sellComputer(productId));
    }

    @Data
    static class ResponseDto {
        private Integer price;
        private String manufacturer;
        private Integer stock;

        public ResponseDto(Computer sellComputer) {
            this.price = sellComputer.getPrice();
            this.manufacturer = sellComputer.getManufacturer();
            this.stock = sellComputer.getStock();
        }
    }
}
