package loadmap.jpapractice.embedded;

import loadmap.jpapractice.jpql.Address;
import loadmap.jpapractice.jpql.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Commit
class ValueCollectionTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("값 타입 내부의 값 변경")
    void value_type_change() {
        Employee employee = new Employee("회사원");
        employee.setHomeAddress(new HomeAddress("homeCity", "street", "zipcode"));

        employee.getFavoriteFoods().add("감자");
        employee.getFavoriteFoods().add("고구마");
        employee.getFavoriteFoods().add("사과");

        employee.getAddressHistory().add(new HomeAddress("old1", "street", "zipcode"));
        employee.getAddressHistory().add(new HomeAddress("old2", "street", "zipcode"));

        em.persist(employee);

        em.flush();
        em.clear();

        System.out.println("========== START ==========");
        Employee findEmployee = em.find(Employee.class, employee.getId());

        // bad example: Not used this sample
        // findEmployee.getHomeAddress().setCity("newCity");

        // good example: recommend this sample
        // findEmployee.setHomeAddress(new HomeAddress("newCity", "newStreet", "newZipCode"));

        // if you want used before data to save new data
        // 아래와 같이 완전히 갈아끼워야 한다.
        HomeAddress oldAddress = findEmployee.getHomeAddress();
        findEmployee.setHomeAddress(new HomeAddress("newAddressCity", oldAddress.getStreet(), oldAddress.getZipcode()));
    }

    @Test
    @DisplayName("값 타입 컬렉션 기본 예시")
    void value_collection() {
        Employee employee = new Employee("회사원");
        employee.setHomeAddress(new HomeAddress("homeCity", "street", "zipcode"));

        employee.getFavoriteFoods().add("감자");
        employee.getFavoriteFoods().add("고구마");
        employee.getFavoriteFoods().add("사과");

        employee.getAddressHistory().add(new HomeAddress("old1", "street", "zipcode"));
        employee.getAddressHistory().add(new HomeAddress("old2", "street", "zipcode"));

        em.persist(employee);
    }

    @Test
    @DisplayName("값 타입 컬렉션 내부 값 변경")
    void value_collection_change_value() {
        Employee employee = new Employee("회사원");
        employee.setHomeAddress(new HomeAddress("homeCity", "street", "zipcode"));

        employee.getFavoriteFoods().add("감자");
        employee.getFavoriteFoods().add("고구마");
        employee.getFavoriteFoods().add("사과");

        employee.getAddressHistory().add(new HomeAddress("old1", "street", "zipcode"));
        employee.getAddressHistory().add(new HomeAddress("old2", "street", "zipcode"));

        em.persist(employee);

        em.flush();
        em.clear();

        System.out.println("========== START ==========");
        Employee findEmployee = em.find(Employee.class, employee.getId());

        /** Set<String> **/
        // 사과 -> 바나나
        // String은 업데이트를 하면 안되고, 할 수도 없다.
        // 컬렉션의 값만 변경해도 데이트베이스에 쿼리가 나가게 된다.
        findEmployee.getFavoriteFoods().remove("사과");
        findEmployee.getFavoriteFoods().add("바나나");

        /** List<Address> **/
        // 기본편 24:31


    }
}