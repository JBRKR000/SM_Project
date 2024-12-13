package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Block;
import pbs.edu.cooperative.service.BlockService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/blocks")
public class BlockController {

    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/{id}")
    public Optional<Block> getBlockById(@PathVariable("id") int id) {
        return blockService.getBlockById(id);
    }

    @PostMapping
    public Block saveBlock(@RequestBody Block block) {
        return blockService.saveBlock(block);
    }

    @GetMapping
    public Page<Block> getAllBlocks(Pageable pageable) {
        return blockService.getAllBlocks(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteBlockById(@PathVariable int id) {
        blockService.deleteBlockById(id);
    }
}
