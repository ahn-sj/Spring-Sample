package sample.simpletest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
