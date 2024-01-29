package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Province;
import BWTEAM2.BW_final.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/{id}")
    public Province findProvinceById(@PathVariable UUID id){
        return provinceService.findById(id);
    }

    @GetMapping
    public Page<Province> findAllProvinces(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "uuid") String orderBy){
        return provinceService.findAllProvinces(page, size, orderBy);
    }
}
