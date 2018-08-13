package pl.akademiakodu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akademiakodu.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
