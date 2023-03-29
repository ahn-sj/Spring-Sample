package springbox.firstclasscollection.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TagTest {

    @Test
    @DisplayName("Tag를 저장한다.")
    void saveTag() throws Exception {
        Tag tag = Tag.of("스터디");
        assertThat(tag.getTagName().getValue()).isEqualTo("스터디");
    }
}
