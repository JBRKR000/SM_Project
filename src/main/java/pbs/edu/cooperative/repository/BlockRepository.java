package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pbs.edu.cooperative.model.Block;

import java.util.List;


public interface BlockRepository extends JpaRepository<Block, Integer> {

    @Query("SELECT b.blockId FROM Block b WHERE b.city = :city AND b.street = :street")
    List<Integer> findEntrancesByBlockAddress(String city, String street);

    @Query("SELECT b.blockId FROM Block b WHERE b.blockId = :blockId AND b.buildingNumber = :buildingNumber")
    Integer findEntranceIdByBlockIdAndEntranceNumber(int blockId, int buildingNumber);
}
