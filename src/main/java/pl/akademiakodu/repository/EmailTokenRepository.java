package pl.akademiakodu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.akademiakodu.model.EmailToken;
@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken,Integer> {
    EmailToken findByToken(String token);
    EmailToken findByUserId(Integer UserId);
}
