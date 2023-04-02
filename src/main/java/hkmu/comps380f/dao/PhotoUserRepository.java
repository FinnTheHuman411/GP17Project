package hkmu.comps380f.dao;

import hkmu.comps380f.model.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoUserRepository extends JpaRepository<ImageUser, String> {
}