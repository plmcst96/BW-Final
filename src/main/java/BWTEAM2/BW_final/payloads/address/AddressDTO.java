package BWTEAM2.BW_final.payloads.address;

import java.util.UUID;

public record AddressDTO(String street, int streetNumber, String district, int zipCode, UUID idTown) {

}
