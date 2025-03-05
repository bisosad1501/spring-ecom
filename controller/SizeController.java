package bisosad.kt2.controller;

import bisosad.kt2.model.Size;
import bisosad.kt2.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public List<Size> getAllSizes() {
        return sizeService.getAllSizes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable Long id) {
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }

    @PostMapping
    public ResponseEntity<Size> createSize(@RequestBody Size size) {
        return ResponseEntity.ok(sizeService.createSize(size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable Long id, @RequestBody Size size) {
        return ResponseEntity.ok(sizeService.updateSize(id, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        sizeService.deleteSize(id);
        return ResponseEntity.noContent().build();
    }
}
