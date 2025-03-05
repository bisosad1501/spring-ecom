package bisosad.kt2.service;

import bisosad.kt2.model.Type;
import bisosad.kt2.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Type getTypeById(Long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Type not found"));
    }

    public Type createType(Type type) {
        return typeRepository.save(type);
    }

    public Type updateType(Long id, Type updatedType) {
        Type type = getTypeById(id);
        type.setName(updatedType.getName());
        type.setDescription(updatedType.getDescription());
        return typeRepository.save(type);
    }

    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
