package springbox.responseresponseentity.presentation;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AnimalController {

    private Map<Long, String> animals = new HashMap<>();

    private static final long KEY = 1L;
    private static final String VALUE = "lion";

    private static final String OK_TEXT = "OK";
    private static final String NO_TEXT = "NO";

    @PostMapping("/api/save")
    public ResponseEntity<AnimalResponse> save() {
        animals.put(KEY, VALUE);

        return ResponseEntity.created(URI.create("/api/" + KEY)).build();
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<AnimalResponse> findOne(@PathVariable Long id) {
        System.out.println("===========");
        return ResponseEntity.ok().body(new AnimalResponse(id));
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animals.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/existence/{id}")
    public ResponseEntity<String> isExist(@PathVariable Long id) {
        if (animals.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(OK_TEXT);
        }
        return ResponseEntity.badRequest().body(NO_TEXT);
    }

    @Getter
    public class AnimalResponse {
        private String animalKind;

        public AnimalResponse(Long id) {
            this.animalKind = animals.get(id);
        }
    }
}
