package pbs.edu.cooperative.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Block;

import java.util.Optional;

@Service
public interface BlockService {
    Optional<Block> getBlockById(int id);
    Block saveBlock(Block block);
    Page<Block> getAllBlocks(Pageable pageable);
    void deleteBlockById(int id);
    void deleteBlock(Block block);
}
