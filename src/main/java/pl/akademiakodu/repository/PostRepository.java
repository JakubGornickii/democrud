package pl.akademiakodu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akademiakodu.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByModerated(boolean moderated);
    List<Post> findByUserId(Integer userId);
}
