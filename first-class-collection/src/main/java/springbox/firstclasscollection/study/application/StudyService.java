package springbox.firstclasscollection.study.application;

import org.springframework.stereotype.Service;
import springbox.firstclasscollection.study.domain.Study;
import springbox.firstclasscollection.study.dto.StudyCreateRequestDto;
import springbox.firstclasscollection.study.dto.StudyCreateResponseDto;
import springbox.firstclasscollection.study.infrastructure.StudyRepository;
import springbox.firstclasscollection.tag.domain.Tags;
import springbox.firstclasscollection.tag.infrastructure.TagRepository;

@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final TagRepository tagRepository;

    public StudyService(StudyRepository studyRepository, TagRepository tagRepository) {
        this.studyRepository = studyRepository;
        this.tagRepository = tagRepository;
    }

    public StudyCreateResponseDto create(StudyCreateRequestDto studyCreateRequestDto) {
        Study study = new Study(studyCreateRequestDto.getTitle());
        study.addTags(new Tags(studyCreateRequestDto.getTags()));

        Study savedStudy = studyRepository.save(study);

        return new StudyCreateResponseDto(savedStudy);
    }

    public Study findOne(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow();
    }

    public String delete(Long studyId) {
        studyRepository.deleteById(studyId);

        return studyId + "번 스터디 모집글 삭제 완료";
    }
}
