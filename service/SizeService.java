package bisosad.kt2.service;

import bisosad.kt2.model.Size;
import bisosad.kt2.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public Size getSizeById(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Size not found"));
    }

    public Size createSize(Size size) {
        return sizeRepository.save(size);
    }

    public Size updateSize(Long id, Size updatedSize) {
        Size size = getSizeById(id);
        size.setValue(updatedSize.getValue());
        size.setDescription(updatedSize.getDescription());
        return sizeRepository.save(size);
    }

    public void deleteSize(Long id) {
        sizeRepository.deleteById(id);
    }
}
