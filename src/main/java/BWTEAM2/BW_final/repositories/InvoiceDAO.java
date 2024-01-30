package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.InvoiceState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceDAO extends JpaRepository<Invoice, UUID> {

    //public Page<Invoice> findByNumber(int number, Pageable pageable);

    public Page<Invoice> findByInvoiceState(InvoiceState state, Pageable pageable);

    public Page<Invoice> findByClient(Client client, Pageable pageable);

    public Page<Invoice> findByDate(LocalDate date, Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE i.amount BETWEEN :minAmount AND :maxAmount")
    List<Invoice> findInvoicesByAmountRange(@Param("minAmount") double minAmount, @Param("maxAmount") double maxAmount);


}