package springbox.firstclasscollection.tag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TagName {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 5;

    @Column(name = "name", nullable = false) // unique = true
    private String value;

    public TagName(String value) {
        this.value = value;
    }

    public static TagName of(String tagName) {
        return new TagName(tagName);
    }
}
