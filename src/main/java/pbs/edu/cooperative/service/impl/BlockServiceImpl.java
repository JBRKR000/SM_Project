package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pbs.edu.cooperative.model.Block;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.repository.BlockRepository;
import pbs.edu.cooperative.service.BlockService;

import java.util.List;
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
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono bloku o id: " + id));
        blockRepository.delete(block);
    }
    @Override
    public void deleteBlock(Block block) {
        blockRepository.delete(block);
    }

    @Override
    public List<Integer> getEntrancesByBlockAddress(String city, String street) {
        return blockRepository.findEntrancesByBlockAddress(city, street);
    }

    @Override
    public Integer getEntranceId(int blockId, int entranceNumber) {
        return blockRepository.findEntranceIdByBlockIdAndEntranceNumber(blockId, entranceNumber);
    }
}
