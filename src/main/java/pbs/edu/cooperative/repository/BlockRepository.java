package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbs.edu.cooperative.model.Block;


public interface BlockRepository extends JpaRepository<Block, Integer> {

}
