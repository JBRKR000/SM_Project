package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pbs.edu.cooperative.model.ApartmentStaircase;

import java.util.List;

@Repository
public interface ApartmentStaircaseRepository extends JpaRepository<ApartmentStaircase, Integer> {

    @Query("SELECT a FROM ApartmentStaircase a WHERE a.block.blockId = :blockId")
    Page<ApartmentStaircase> findByBlockId(int blockId, Pageable pageable);
}
