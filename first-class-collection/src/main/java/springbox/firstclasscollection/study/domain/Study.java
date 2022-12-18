package springbox.firstclasscollection.study.domain;

import lombok.*;
import springbox.firstclasscollection.tag.domain.Tags;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@AllArgsConstructor
public class Study {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    private String title;

    @Embedded
    private Tags tags = new Tags();

    public Study(String title) {
        this.title = title;
    }

    public void addTags(Tags tags) {
        this.tags = tags;
    }
}
