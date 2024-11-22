package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbs.edu.cooperative.model.Flat;


public interface FlatRepository extends JpaRepository<Flat, Integer> {

}
