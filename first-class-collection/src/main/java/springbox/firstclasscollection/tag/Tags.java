package springbox.firstclasscollection.tag;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateNotNull(tags);
        this.tags = tags;
    }

    private void validateNotNull(List<Tag> tags) {
        if(Objects.isNull(tags)) {
            throw new IllegalArgumentException("tags는 null이 될 수 없습니다.");
        }
    }

    public int size() {
        return tags.size();
    }

    public List<Tag> toList() {
        return Collections.unmodifiableList(tags);
    }

    public static Tags of(List<Tag> tags) {
        return new Tags(tags);
    }
}
