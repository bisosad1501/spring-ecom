package bisosad.kt2.service;

import bisosad.kt2.model.Shoes;
import bisosad.kt2.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoesService {

    @Autowired
    private ShoesRepository shoesRepository;

    public List<Shoes> getAllShoes() {
        return shoesRepository.findAll();
    }

    public Shoes getShoesById(Long id) {
        return shoesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shoes not found"));
    }

    public Shoes createShoes(Shoes shoes) {
        return shoesRepository.save(shoes);
    }

    public Shoes updateShoes(Long id, Shoes updatedShoes) {
        Shoes shoes = getShoesById(id);
        shoes.setName(updatedShoes.getName());
        shoes.setMaterial(updatedShoes.getMaterial());
        return shoesRepository.save(shoes);
    }

    public void deleteShoes(Long id) {
        shoesRepository.deleteById(id);
    }
}
