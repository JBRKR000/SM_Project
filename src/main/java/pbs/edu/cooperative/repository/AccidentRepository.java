package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pbs.edu.cooperative.model.Accident;

import java.awt.print.Pageable;
import java.util.List;


public interface AccidentRepository extends JpaRepository<Accident, Integer> {

    @Query("SELECT a FROM Accident a WHERE a.flat.flatId = :flat_id")
    List<Accident> accidentList(@Param("flat_id") Integer flat_id, Pageable pageable);

}
