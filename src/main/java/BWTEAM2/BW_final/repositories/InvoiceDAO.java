package BWTEAM2.BW_final.repositories;

import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.InvoiceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceDAO extends JpaRepository<Invoice, UUID> {

    List<Invoice> findByNumber(int number);

    List<Invoice> findByInvoiceState(InvoiceState state);

    List<Invoice> findByClient(Client client);

    List<Invoice> findByDate(LocalDate date);


}