package springbox.firstclasscollection.tag.domain;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Tags {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    public Tags(List<Tag> tags) {
        validateSize(tags);
        this.tags = tags;
    }

    private void validateSize(List<Tag> tags) {
        if(tags.size() > 5) {
            throw new IllegalArgumentException("태그는 최대 5개까지 가능합니다.");
        }
    }
}
