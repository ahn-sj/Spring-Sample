package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryTest {

    MemberRepository repository = new MemberRepository();

    @Test
    void crud() throws SQLException {
        // create
        String memberId = "memberV1";

        Member member = new Member(memberId, 10000);
        repository.save(member);

        // read one
        Member findMember = repository.findById(member.getMemberId());

        log.info("member == findMember : {}", member == findMember);          // false
        log.info("member equals findMember : {}", member.equals(findMember)); // true Lombok(EqualsAndHashCode)

        assertThat(findMember.getMemberId()).isEqualTo(memberId);

        // read all
        repository.save(new Member("memberV2", 20000));

        List<Member> members = repository.findAll();
        for (Member m : members) {
            System.out.println("member = " + m);
        }
        assertThat(members.size()).isEqualTo(2);

        // update
        repository.update(member.getMemberId(), 20000);

        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        List<Member> members1 = repository.findAll();

        assertThatThrownBy(() -> {
            repository.findById(member.getMemberId());
        }).isInstanceOf(NoSuchElementException.class)
                .hasMessage("member not found memberId = " + member.getMemberId());
        assertThat(members1.size()).isEqualTo(1);
    }
}