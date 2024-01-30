package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepo extends JpaRepository<Address, UUID> {
    Optional<Address> findById(UUID uuid);
}
