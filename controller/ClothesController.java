package bisosad.kt2.controller;

import bisosad.kt2.model.Clothes;
import bisosad.kt2.service.ClothesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
public class ClothesController {

    @Autowired
    private ClothesService clothesService;

    @GetMapping
    public List<Clothes> getAllClothes() {
        return clothesService.getAllClothes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clothes> getClothesById(@PathVariable Long id) {
        return ResponseEntity.ok(clothesService.getClothesById(id));
    }

    @PostMapping
    public ResponseEntity<Clothes> createClothes(@RequestBody Clothes clothes) {
        return ResponseEntity.ok(clothesService.createClothes(clothes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clothes> updateClothes(@PathVariable Long id, @RequestBody Clothes clothes) {
        return ResponseEntity.ok(clothesService.updateClothes(id, clothes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClothes(@PathVariable Long id) {
        clothesService.deleteClothes(id);
        return ResponseEntity.noContent().build();
    }
}
