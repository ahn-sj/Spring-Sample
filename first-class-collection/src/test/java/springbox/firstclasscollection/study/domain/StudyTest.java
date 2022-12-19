package springbox.firstclasscollection.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbox.firstclasscollection.study.infrastructure.StudyRepository;
import springbox.firstclasscollection.tag.domain.Tag;
import springbox.firstclasscollection.tag.domain.Tags;
import springbox.firstclasscollection.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StudyTest {
    @Autowired TagRepository tagRepository;
    @Autowired StudyRepository studyRepository;

    @Test
    @DisplayName("스터디 모집글 생성")
    public void 스터디_모집글_생성() throws Exception {
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tagList.add(tagRepository.save(new Tag("알고리즘" + i)));
        }

        Study study = studyRepository.save(new Study("알고"));
        study.addTags(new Tags(tagList));

        System.out.println("study = " + study);
    }
}