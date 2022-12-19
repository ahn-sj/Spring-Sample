package springbox.firstclasscollection.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.firstclasscollection.tag.domain.Tag;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class StudyCreateRequestDto {
    private String title;
    private List<Tag> tags = new ArrayList<>();
}
