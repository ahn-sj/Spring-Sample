package loadmap.jpapractice.jpql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("jpql 쿼리를 날린다.")
    void jpql_start() throws Exception {
        List<Member> members = memberRepository.findAllByUsernameIsKim();
        members.forEach(System.out::println);
    }

}