package hkmu.comps380f.dao;

import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByImageId(long imageId);
    List<Comment> findByUsername(String username);
}
