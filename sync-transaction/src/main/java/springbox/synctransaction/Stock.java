package springbox.synctransaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
    @Id @GeneratedValue
    private Long id;

    private int stock;

    public Stock(int stock) {
        this.stock = stock;
    }

    public void decreaseStock() {
        stock = stock - 1;
    }
}
