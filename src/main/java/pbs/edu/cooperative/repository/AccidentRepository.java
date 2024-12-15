package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pbs.edu.cooperative.model.Accident;

import java.util.List;


public interface AccidentRepository extends JpaRepository<Accident, Integer> {
    Page<Accident> findByFlatFlatId(int flatId, Pageable pageable);

    @Query("select a from Accident a where a.isResolved == false")
    Page<Accident> findAllByResolved(Pageable pageable);

}
