package springbox.responseresponseentity.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private Map<Long, Student> students = new HashMap<>();
    private static final long KEY = 1L;

    @PostConstruct
    public void init() {
        Student student1 = new Student(0, "mummumjae", false);
        Student student2 = new Student(3, "mumjae", true);

        students.put(100L, student1);
        students.put(101L, student2);
    }

    @PostMapping
    public ResponseEntity<Student> save() {
        students.put(KEY, new Student(1, "jae", true));

        return ResponseEntity.created(URI.create("/api/student/" + KEY)).build();
    }

    @DeleteMapping("/v1/{id}") // 200 OK
    public ResponseEntity<String> v1Delete(@PathVariable Long id) {
        Student student = students.get(id);
        student.changeValid();

        String body = student.getGrade() + "학년 " + student.getName() + " 의 계정은 비활성화 되었습니다.";

        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/v2/{id}") // 204 No Content
    public ResponseEntity<Void> v2Delete(@PathVariable Long id) {
        students.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<Long, Student>> findAll() {
        return ResponseEntity.ok(students);
    }

    static class Student {
        private int grade;
        private String name;
        private boolean isValid;

        public Student(int grade, String name, boolean isValid) {
            this.grade = grade;
            this.name = name;
            this.isValid = isValid;
        }

        public void changeValid() {
            isValid = !isValid;
        }

        public int getGrade() {
            return grade;
        }

        public String getName() {
            return name;
        }

        public boolean isValid() {
            return isValid;
        }
    }
}
