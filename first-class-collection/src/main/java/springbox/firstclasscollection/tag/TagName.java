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

    private static final int MAX_TAG_NAME_SIZE = 10;

    @Column(name = "name", nullable = false) // unique = true
    private String value;

    public TagName(String value) {
        validateTagNameSize(value);
        this.value = value;
    }

    private void validateTagNameSize(String value) {
        if(value.length() > MAX_TAG_NAME_SIZE) {
            throw new IllegalArgumentException("태그의 이름은 최대 10글자입니다.");
        }
    }

    public static TagName of(String tagName) {
        return new TagName(tagName);
    }
}
