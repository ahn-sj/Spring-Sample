package springbox.firstclasscollection.study.application;

import org.springframework.stereotype.Service;
import springbox.firstclasscollection.study.domain.Study;
import springbox.firstclasscollection.study.dto.StudyCreateDto;
import springbox.firstclasscollection.study.infrastructure.StudyRepository;
import springbox.firstclasscollection.tag.domain.Tags;
import springbox.firstclasscollection.tag.repository.TagRepository;

@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final TagRepository tagRepository;

    public StudyService(StudyRepository studyRepository, TagRepository tagRepository) {
        this.studyRepository = studyRepository;
        this.tagRepository = tagRepository;
    }

    public Study create(StudyCreateDto studyCreateDto) {
        Study study = new Study(studyCreateDto.getTitle());
        study.addTags(new Tags(studyCreateDto.getTags()));

        return studyRepository.save(study);
    }

    public Study findOne(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow();
    }
}
