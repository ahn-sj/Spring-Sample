package springbox.jpaflushtest.coffee;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class Coffee {

    @Id
    @GeneratedValue
    private Long id;

    private String kind;

    public Coffee(String kind) {
        this.kind = kind;
    }
}
