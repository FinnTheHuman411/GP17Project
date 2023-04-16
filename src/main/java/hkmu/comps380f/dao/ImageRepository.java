package hkmu.comps380f.dao;

import hkmu.comps380f.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUsername(String username);
    List<Image> deleteByUsername(String username);

}
