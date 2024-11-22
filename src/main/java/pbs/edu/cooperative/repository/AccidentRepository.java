package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pbs.edu.cooperative.model.Accident;

import java.util.List;


public interface AccidentRepository extends JpaRepository<Accident, Integer> {

}
