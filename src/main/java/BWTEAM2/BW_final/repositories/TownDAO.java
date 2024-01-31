package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TownDAO extends JpaRepository<Town, UUID> {
    Optional<Town> findByName(String name);
}