package pl.akademiakodu.repository;

import org.springframework.stereotype.Repository;
import pl.akademiakodu.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {
}
