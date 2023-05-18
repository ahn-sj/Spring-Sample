package loadmap.jpapractice.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeAddress {
    private String city;
    private String street;
    private String zipcode;
}
