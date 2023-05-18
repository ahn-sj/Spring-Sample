package loadmap.jpapractice.embedded;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Employee {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "employee_name")
    private String name;

    @Embedded
    private HomeAddress homeAddress;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "favorite_food", joinColumns =
        @JoinColumn(name = "member_id")
    )
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "address", joinColumns =
    @JoinColumn(name = "member_id"))
    private List<HomeAddress> addressHistory = new ArrayList<>();

    public Employee(String name) {
        this.name = name;
    }
}
