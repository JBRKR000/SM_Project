package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Block;
import pbs.edu.cooperative.service.BlockService;
import pbs.edu.cooperative.service.FlatService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/blocks")
public class BlockController {

    private final BlockService blockService;
    private final FlatService flatService;

    @Autowired
    public BlockController(BlockService blockService, FlatService flatService) {
        this.blockService = blockService;
        this.flatService = flatService;
    }

    @GetMapping("/{id}")
    public Optional<Block> getBlockById(@PathVariable("id") int id) {
        return blockService.getBlockById(id);
    }

    @PostMapping
    public Block saveBlock(@RequestBody Block block) {
        return blockService.saveBlock(block);
    }

    @PutMapping("/{id}")
    public Block updateBlock(@PathVariable int id, @RequestBody Map<String, Object> blockData) {
        // Pobierz istniejącego blocka
        Optional<Block> existingBlockOpt = blockService.getBlockById(id);

        if (existingBlockOpt.isEmpty()) {
            throw new RuntimeException("Block not found with id: " + id);
        }

        Block block = existingBlockOpt.get();

        // Aktualizuj tylko wybrane pola
        if (blockData.containsKey("city")) {
            block.setCity((String) blockData.get("city"));
        }
        if (blockData.containsKey("street")) {
            block.setStreet((String) blockData.get("street"));
        }
        if (blockData.containsKey("buildingNumber")) {
            System.out.println(blockData.get("buildingNumber"));
            block.setBuildingNumber((Integer) blockData.get("buildingNumber"));
        }
        else{
            System.out.println(blockData.get("buildingNumber"));
        }

        // Zapisz zmiany
        return blockService.saveBlock(block);
    }


    @GetMapping
    public Page<Block> getAllBlocks(Pageable pageable) {
        return blockService.getAllBlocks(pageable);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteBlockById(@PathVariable int id) {
        blockService.deleteBlockById(id);
    }

    // Endpoint do pobierania numerów klatek na podstawie adresu bloku
    @GetMapping("/entrances")
    public List<Integer> getEntrancesByBlockAddress(@RequestBody Map<String, String> request) {
        String city = request.get("city");
        String street = request.get("street");
        return blockService.getEntrancesByBlockAddress(city, street);
    }

    // Endpoint do pobierania id klatki na podstawie block_id i numeru klatki
    @GetMapping("/entrance-id")
    public Integer getEntranceId(@RequestParam int blockId, @RequestParam int entranceNumber) {
        return blockService.getEntranceId(blockId, entranceNumber);
    }

    // Endpoint do pobierania wszystkich niezajętych flat_id na podstawie id klatki
    @GetMapping("/unoccupied-flats")
    public List<Integer> getUnoccupiedFlats(@RequestParam int entranceId) {
        return flatService.getUnoccupiedFlats(entranceId);
    }
}
