package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Province;
import BWTEAM2.BW_final.exception.NotFoundException;
import BWTEAM2.BW_final.repositories.ProvinceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceDAO provinceDAO;

    public Province findById(UUID id){
        return provinceDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Page<Province> findAllProvinces(int page, int size, String orderBy){
        if(size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return provinceDAO.findAll(pageable);
    }


    public Province findByName(String name) {
       return provinceDAO.findByName(name).orElseThrow(()-> new NotFoundException("not found" + name));
    }
}
