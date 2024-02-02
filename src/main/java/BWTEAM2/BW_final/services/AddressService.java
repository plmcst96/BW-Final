package BWTEAM2.BW_final.services;


import BWTEAM2.BW_final.entities.Address;
import BWTEAM2.BW_final.entities.Town;
import BWTEAM2.BW_final.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import BWTEAM2.BW_final.payloads.address.AddressDTO;
import BWTEAM2.BW_final.repositories.AddressRepo;


import java.util.UUID;


@Service
public class AddressService {
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    TownService townService;

    public Address save(AddressDTO body) {
        Address address = new Address();
        Town town = townService.findByName(body.nameTown());
        address.setStreet(body.street());
        address.setStreetNumber(body.streetNumber());
        address.setDistrict(body.district());
        address.setZipCode(body.zipCode());
        address.setTown(town);
        return addressRepo.save(address);
    }

    public Address findById(UUID id) {
        return addressRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Address> findAll(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return addressRepo.findAll(pageable);
    }

    public Address updateById(UUID  id , AddressDTO body) {
        Address address = addressRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        Town town = townService.findByName(body.nameTown());
        address.setStreet(body.street());
        address.setStreetNumber(body.streetNumber());
        address.setDistrict(body.district());
        address.setZipCode(body.zipCode());
        address.setTown(town);
        return addressRepo.save(address);
    }

    public void deleteById(UUID id) {
        Address address = addressRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
        addressRepo.delete(address);
    }


}
