package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Town;
import BWTEAM2.BW_final.exception.NotFoundException;
import BWTEAM2.BW_final.repositories.TownDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TownService {
    @Autowired
    private TownDAO townDAO;

    public Town findById(UUID id){
        return townDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }


    public Page<Town> findAllTown(int page, int size, String orderBy){
        if(size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return townDAO.findAll(pageable);
    }



}