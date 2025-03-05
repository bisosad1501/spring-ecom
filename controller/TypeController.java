package bisosad.kt2.controller;

import bisosad.kt2.model.Type;
import bisosad.kt2.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public List<Type> getAllTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(typeService.getTypeById(id));
    }

    @PostMapping
    public ResponseEntity<Type> createType(@RequestBody Type type) {
        return ResponseEntity.ok(typeService.createType(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Long id, @RequestBody Type type) {
        return ResponseEntity.ok(typeService.updateType(id, type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return ResponseEntity.noContent().build();
    }
}
