package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Town;
import BWTEAM2.BW_final.services.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/town")
public class TownController {
    @Autowired
    private TownService townService;

    @GetMapping("/{id}")
    public Town findTownById(@PathVariable UUID id){
        return townService.findById(id);
    }

    @GetMapping
    public Page<Town> findAllTown(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "uuid") String orderBy){
        return townService.findAllTown(page, size, orderBy);
    }
}
