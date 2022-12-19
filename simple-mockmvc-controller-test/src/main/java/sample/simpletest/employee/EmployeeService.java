package sample.simpletest.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee enterCompany(String nickname) {
        System.out.println("nickname = " + nickname);
        Employee saveEmployee = employeeRepository.save(new Employee(nickname));
        return saveEmployee;
    }

    @Transactional
    public Boolean workOnOff(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow().changeEnter();
    }

    public List<Employee> find() {
        return employeeRepository.findAll();
    }

    public Employee findOne(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow();
    }
}
