package springbox.firstclasscollection.prev.oldstudy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OldStudy {
    // 게시물

    @Id @GeneratedValue
    private Long id;


    List<String> tags = new ArrayList<>();




}
