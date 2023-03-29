package springbox.firstclasscollection.tag;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Tags {

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        this.tags = tags;
    }

    private ArrayList<Tag> validateSameTags(List<Tag> tags) {
        return new ArrayList<>(new HashSet<>(tags));
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
