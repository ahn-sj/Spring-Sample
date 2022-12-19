package sample.simpletest.employee;

import lombok.*;
import sample.simpletest.utils.BooleanToStringConverter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@ToString
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;

    private String nickname;

    @Convert(converter = BooleanToStringConverter.class)
    private Boolean isEnter;

    public Employee(String nickname) {
        this.employeeId = UUID.randomUUID().toString().substring(0, 8);
        this.nickname = nickname;
        this.isEnter = true;
    }

    public Boolean changeEnter() {
        isEnter = !isEnter;
        return isEnter;
    }
}
