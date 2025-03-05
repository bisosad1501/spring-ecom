package bisosad.kt2.controller;

import bisosad.kt2.model.Electronics;
import bisosad.kt2.service.ElectronicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/electronics")
public class ElectronicsController {

    @Autowired
    private ElectronicsService electronicsService;

    @GetMapping
    public List<Electronics> getAllElectronics() {
        return electronicsService.getAllElectronics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Electronics> getElectronicsById(@PathVariable Long id) {
        return ResponseEntity.ok(electronicsService.getElectronicsById(id));
    }

    @PostMapping
    public ResponseEntity<Electronics> createElectronics(@RequestBody Electronics electronics) {
        return ResponseEntity.ok(electronicsService.createElectronics(electronics));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Electronics> updateElectronics(@PathVariable Long id, @RequestBody Electronics electronics) {
        return ResponseEntity.ok(electronicsService.updateElectronics(id, electronics));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElectronics(@PathVariable Long id) {
        electronicsService.deleteElectronics(id);
        return ResponseEntity.noContent().build();
    }
}
