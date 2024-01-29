package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientsDAO extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
    Page<Client> findByName(String name, Pageable pageable);
    Page<Client> findBySurname(String surname, Pageable pageable);
    Page<Client> findByNameAndSurname(String name,String surname, Pageable pageable);

    public Page<Client> findByAnnualRevenueGreaterThan(double annualRevenue, Pageable pageable);
    public Page<Client> findByBusinessNameContaining(String businessName, Pageable pageable);

    public Page<Client> findByInputDate(LocalDate inputDate, Pageable pageable);

    public Page<Client> findByLastContactDate(LocalDate lastContactDate, Pageable pageable);
    public Page<Client> findByAnnualRevenueLessThan(double annualRevenue, Pageable pageable);


}
