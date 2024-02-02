package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Address;
import BWTEAM2.BW_final.exception.BadRequestException;
import BWTEAM2.BW_final.payloads.address.AddressDTO;
import BWTEAM2.BW_final.payloads.address.AddressResponseDTO;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import BWTEAM2.BW_final.services.AddressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping
    public Page<Address> getAddress(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "uuid") String orderBy) {
        return addressService.findAll(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable UUID id) {
        return addressService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Address getAddressByIdAndUpdate(@PathVariable UUID id, @RequestBody AddressDTO addressPayload) {
        return addressService.updateById(id, addressPayload);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAddressByIdAndDelete(@PathVariable UUID id) {
        addressService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public AddressResponseDTO create(@RequestBody @Validated AddressDTO address, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Address newAddress = addressService.save(address);
            return new AddressResponseDTO(newAddress.getUuid());
        }
    }
}