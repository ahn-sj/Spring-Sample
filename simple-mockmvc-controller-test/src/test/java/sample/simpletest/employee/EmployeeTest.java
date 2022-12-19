package sample.simpletest.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeTest {

    @Test
    @DisplayName("회사 합격")
    public void 취업_성공() throws Exception {
        // given
        Employee employee = new Employee("르탄");

        // when & then
        assertThat(employee.getEmployeeId()).isNotNull();
        assertThat(employee.getNickname()).isEqualTo("르탄");
    }

}