package springbox.jpan1problem.jojoldu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcademyServiceTest {

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private AcademyService academyService;

    @Test
    public void Academy여러개를_joinFetch로_가져온다() throws Exception {

        List<Academy> academies = new ArrayList<>();

        for(int i=0;i<10;i++){
            Academy academy = Academy.builder()
                    .name("강남스쿨"+i)
                    .build();

            academy.addSubject(Subject.builder().name("자바웹개발" + i).build());
            academy.addSubject(Subject.builder().name("파이썬자동화" + i).build()); // Subject를 추가
            academies.add(academy);
        }

        academyRepository.saveAll(academies);

        //given
        List<Academy> academies2 = academyRepository.findAllJoinFetch();
        List<String> subjectNames = academyService.findAllSubjectNamesByJoinFetch();

        //then
        Assertions.assertThat(academies2.size()).isEqualTo(20);
        Assertions.assertThat(subjectNames.size()).isEqualTo(20);
    }

}