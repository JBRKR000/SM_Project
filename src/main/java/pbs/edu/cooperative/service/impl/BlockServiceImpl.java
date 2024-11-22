package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Block;
import pbs.edu.cooperative.repository.BlockRepository;
import pbs.edu.cooperative.service.BlockService;

import java.util.Optional;

@Service
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockServiceImpl(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Override
    public Optional<Block> getBlockById(int id) {
        return blockRepository.findById(id);
    }

    @Override
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    @Override
    public Page<Block> getAllBlocks(Pageable pageable) {
        return blockRepository.findAll(pageable);
    }

    @Override
    public void deleteBlockById(int id) {
        blockRepository.deleteById(id);
    }

    @Override
    public void deleteBlock(Block block) {
        blockRepository.delete(block);
    }
}
