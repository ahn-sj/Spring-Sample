package springbox.exceptionhandle.recover.printer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Printer {

    @Id @GeneratedValue
    private Long id;

    private int paper;

    @Version
    private Integer version;

    public Printer(int paper) {
        this.paper = paper;
    }

    public void decreasePaper() {
        paper -= 1;
    }
}
