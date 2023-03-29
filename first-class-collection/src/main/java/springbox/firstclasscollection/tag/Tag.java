package springbox.firstclasscollection.tag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.firstclasscollection.BaseEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "tag_name_unique",
        columnNames="name"))
public class Tag extends BaseEntity {

    @Embedded
    private TagName tagName;

    public Tag(TagName tagName) {
        this.tagName = tagName;
    }

    public static Tag of(String tagName) {
        return new Tag(TagName.of(tagName));
    }

}
