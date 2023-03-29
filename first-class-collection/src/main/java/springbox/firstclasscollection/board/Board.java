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
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    private String title;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardTag> boardTags = new ArrayList<>();

    @Builder
    public Board(String title, Tags tags) {
        this.title = title;
        this.boardTags = castTagsToBoardTag(tags);
    }

    private List<BoardTag> castTagsToBoardTag(Tags tags) {
        return tags.toList().stream()
                .map(tag -> BoardTag.of(this, tag))
                .collect(Collectors.toList());
    }
}
