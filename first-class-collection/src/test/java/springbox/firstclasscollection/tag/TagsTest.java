package springbox.firstclasscollection.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TagsTest {

    @Test
    @DisplayName("Tags를 저장한다.")
    void saveTags() throws Exception {
        Tags tags = Tags.of(Arrays.asList(
                Tag.of("모각공"), Tag.of("면접스터디"), Tag.of("독서스터디")));

        // List<Tag> -> Tag -> Tag.tagName -> value
        assertThat(tags.toList())
                .extracting("tagName")
                .extracting("value")
                .containsExactly("모각공", "면접스터디", "독서스터디");
        // containsOnly    >> 순서, 중복 무시 + 원소값, 개수 정확히 일치
        // containsExactly >> 순서, 중복 허용X, 원소값, 개수 정확히 일치
    }
}
