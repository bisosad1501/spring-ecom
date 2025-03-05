package bisosad.kt2.controller;

import bisosad.kt2.model.Shoes;
import bisosad.kt2.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ShoesController {

    @Autowired
    private ShoesService shoesService;

    @GetMapping
    public List<Shoes> getAllShoes() {
        return shoesService.getAllShoes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shoes> getShoesById(@PathVariable Long id) {
        return ResponseEntity.ok(shoesService.getShoesById(id));
    }

    @PostMapping
    public ResponseEntity<Shoes> createShoes(@RequestBody Shoes shoes) {
        return ResponseEntity.ok(shoesService.createShoes(shoes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shoes> updateShoes(@PathVariable Long id, @RequestBody Shoes shoes) {
        return ResponseEntity.ok(shoesService.updateShoes(id, shoes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoes(@PathVariable Long id) {
        shoesService.deleteShoes(id);
        return ResponseEntity.noContent().build();
    }
}
