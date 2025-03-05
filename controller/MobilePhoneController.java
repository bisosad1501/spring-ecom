package bisosad.kt2.controller;

import bisosad.kt2.model.MobilePhone;
import bisosad.kt2.service.MobilePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobile-phones")
public class MobilePhoneController {

    @Autowired
    private MobilePhoneService mobilePhoneService;

    @GetMapping
    public List<MobilePhone> getAllMobilePhones() {
        return mobilePhoneService.getAllMobilePhones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobilePhone> getMobilePhoneById(@PathVariable Long id) {
        return ResponseEntity.ok(mobilePhoneService.getMobilePhoneById(id));
    }

    @PostMapping
    public ResponseEntity<MobilePhone> createMobilePhone(@RequestBody MobilePhone mobilePhone) {
        return ResponseEntity.ok(mobilePhoneService.createMobilePhone(mobilePhone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MobilePhone> updateMobilePhone(@PathVariable Long id, @RequestBody MobilePhone mobilePhone) {
        return ResponseEntity.ok(mobilePhoneService.updateMobilePhone(id, mobilePhone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobilePhone(@PathVariable Long id) {
        mobilePhoneService.deleteMobilePhone(id);
        return ResponseEntity.noContent().build();
    }
}
