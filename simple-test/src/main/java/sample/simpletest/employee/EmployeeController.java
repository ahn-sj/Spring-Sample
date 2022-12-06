package sample.simpletest.employee;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sample.simpletest.utils.BooleanToStringConverter;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/home")
    public String responseHome() {
        return "Home";
    }

    @GetMapping("/{employeeId}")
    public ResponseDto getEmployee(@PathVariable Long employeeId) {
        return new ResponseDto(employeeService.findOne(employeeId));
    }

    @GetMapping("/employees")
    public List<ResponseDto> getEmployees() {
        List<ResponseDto> responseDto = new ArrayList<>();
        List<Employee> employees = employeeService.find();

        for (Employee employee : employees) {
            responseDto.add(new ResponseDto(employee));
        }

        return responseDto;
    }

    @PostMapping("/join")
    public Employee join(@RequestBody String nickname) {
        System.out.println("EmployeeController.join");
        System.out.println("nickname = " + nickname);
        Employee employee = employeeService.enterCompany(nickname);
        System.out.println("employee 2 = " + employee);
        return employee;
    }

    @GetMapping("/check/{employeeId}")
    public Boolean checkInOut(@PathVariable Long employeeId) {
        return employeeService.workOnOff(employeeId);
    }

    @Getter
    static class ResponseDto {
        private String employeeId;

        private String nickname;

        @Convert(converter = BooleanToStringConverter.class)
        private Boolean isEnter;

        public ResponseDto(Employee employee) {
            System.out.println("employee = " + employee);

            this.employeeId = employee.getEmployeeId();
            this.nickname = employee.getNickname();
            this.isEnter = employee.getIsEnter();
        }
    }
}
