package springbox.firstclasscollection.study.presentation;

import org.springframework.web.bind.annotation.*;
import springbox.firstclasscollection.study.domain.Study;
import springbox.firstclasscollection.study.dto.StudyCreateRequestDto;
import springbox.firstclasscollection.study.application.StudyService;
import springbox.firstclasscollection.study.dto.StudyCreateResponseDto;

@RestController
@RequestMapping("/api")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/{studyId}")
    public Study findOne(@PathVariable Long studyId) {
        return studyService.findOne(studyId);
    }

    @PostMapping("/save")
    public StudyCreateResponseDto create(@RequestBody StudyCreateRequestDto studyCreateRequestDto) {
        return studyService.create(studyCreateRequestDto);
    }

    @DeleteMapping("/{studyId}")
    public String delete(@PathVariable Long studyId) {
        return studyService.delete(studyId);
    }
}
