package hkmu.comps380f.dao;

import hkmu.comps380f.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Image, String> {
    List<Image> findByUsername(String username);
    List<Image> deleteByUsername(String username);
}
