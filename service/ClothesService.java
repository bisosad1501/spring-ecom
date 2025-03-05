package bisosad.kt2.service;

import bisosad.kt2.model.Clothes;
import bisosad.kt2.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;

    public List<Clothes> getAllClothes() {
        return clothesRepository.findAll();
    }

    public Clothes getClothesById(Long id) {
        return clothesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clothes not found"));
    }

    public Clothes createClothes(Clothes clothes) {
        return clothesRepository.save(clothes);
    }

    public Clothes updateClothes(Long id, Clothes updatedClothes) {
        Clothes clothes = getClothesById(id);
        clothes.setName(updatedClothes.getName());
        clothes.setMaterial(updatedClothes.getMaterial());
        return clothesRepository.save(clothes);
    }

    public void deleteClothes(Long id) {
        clothesRepository.deleteById(id);
    }
}
