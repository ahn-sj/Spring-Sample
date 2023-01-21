package springbox.flywayexample;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dirX;
    private int dirY;

    public Point() {}

    public Point(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }
}
