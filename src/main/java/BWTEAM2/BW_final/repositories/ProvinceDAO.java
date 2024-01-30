package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProvinceDAO extends JpaRepository<Province, UUID> {
    Optional<Province> findByProvinceCode(String provinceCode);
    Optional<Province> findByName(String name);
}
