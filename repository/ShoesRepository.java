package bisosad.kt2.repository;

import bisosad.kt2.model.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {
}
