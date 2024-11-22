package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pbs.edu.cooperative.model.ApartmentStaircase;

@Repository
public interface ApartmentStaircaseRepository extends JpaRepository<ApartmentStaircase, Integer> {
}
