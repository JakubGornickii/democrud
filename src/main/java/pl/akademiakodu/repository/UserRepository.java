package pl.akademiakodu.repository;

import pl.akademiakodu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findByActive(Integer active) ;
}
