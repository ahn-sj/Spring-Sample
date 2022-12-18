package springbox.firstclasscollection.study.controller;

import org.springframework.web.bind.annotation.*;
import springbox.firstclasscollection.study.domain.Study;
import springbox.firstclasscollection.study.dto.StudyCreateDto;
import springbox.firstclasscollection.study.service.StudyService;

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
    public Study create(@RequestBody StudyCreateDto studyCreateDto) {
        return studyService.create(studyCreateDto);
    }
}
