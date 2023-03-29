package springbox.firstclasscollection.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.firstclasscollection.BaseEntity;
import springbox.firstclasscollection.boardtag.BoardTag;
import springbox.firstclasscollection.tag.Tags;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    private static int MAX_TAG_SIZE = 5;

    private String title;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardTag> boardTags = new ArrayList<>();

    @Builder
    public Board(String title, Tags tags) {
        this.title = title;
        if(Objects.nonNull(tags)) {
            this.boardTags = setTags(tags);
        }
    }

    private List<BoardTag> setTags(Tags tags) {
        validateTagSize(tags);
        return castTagsToBoardTag(tags);
    }

    private void validateTagSize(Tags tags) {
        if(tags.size() > MAX_TAG_SIZE) {
            throw new IllegalArgumentException("게시물에는 최대 5개의 태그만 가능합니다.");
        }
    }

    private List<BoardTag> castTagsToBoardTag(Tags tags) {
        return tags.toList().stream()
                .map(tag -> BoardTag.of(this, tag))
                .collect(Collectors.toList());
    }
}
