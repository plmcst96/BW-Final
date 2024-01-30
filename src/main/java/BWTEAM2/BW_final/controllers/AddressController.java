package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Address;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import BWTEAM2.BW_final.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @GetMapping("/byTown/{townId}")
    public List<Address> getAddressesByTown(@PathVariable Long townId) {
        return addressService.getAddressesByTown(townId);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}