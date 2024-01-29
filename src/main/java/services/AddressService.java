package services;


import BWTEAM2.BW_final.entities.Address;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import payload.AddressDTO;
import repositories.AddressRepo;

import java.awt.print.Pageable;
import java.util.UUID;


@Service
public class AddressService {
@Autowired
    AddressRepo addressRepo;

    public Address findById(UUID id) {
        return AddressRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Address> findAll(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return AddressRepo.findAll(pageable);
    }
    public Address updateById(UUID id, AddressDTO body) {
        Address address = AddressRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        address.setStreet(body.street());
        address.setStreetNumber(body.streetNumber());
        address.setDistrict(body.district());
        address.setZipCode(body.zipCode());
        return AddressRepo.save(address);
    }

    public void deleteById(UUID id) {
        Address address = AddressRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        AddressRepo.delete(address);
    }





}
