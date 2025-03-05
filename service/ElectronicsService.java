package bisosad.kt2.service;

import bisosad.kt2.model.Electronics;
import bisosad.kt2.repository.ElectronicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectronicsService {

    @Autowired
    private ElectronicsRepository electronicsRepository;

    public List<Electronics> getAllElectronics() {
        return electronicsRepository.findAll();
    }

    public Electronics getElectronicsById(Long id) {
        return electronicsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Electronics not found"));
    }

    public Electronics createElectronics(Electronics electronics) {
        return electronicsRepository.save(electronics);
    }

    public Electronics updateElectronics(Long id, Electronics updatedElectronics) {
        Electronics electronics = getElectronicsById(id);
        electronics.setName(updatedElectronics.getName());
        electronics.setBrand(updatedElectronics.getBrand());
        return electronicsRepository.save(electronics);
    }

    public void deleteElectronics(Long id) {
        electronicsRepository.deleteById(id);
    }
}
