package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientsDAO extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
}
