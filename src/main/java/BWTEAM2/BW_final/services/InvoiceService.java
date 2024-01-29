package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.repositories.InvoiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceDAO invoiceDAO;

    @Autowired
    public InvoiceService(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }

    public Invoice getInvoiceById(UUID uuid) {
        return invoiceDAO.findById(uuid).orElse(null);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceDAO.save(invoice);
    }

    public Invoice updateInvoice(UUID uuid, Invoice updatedInvoice) {
        Invoice existingInvoice = invoiceDAO.findById(uuid).orElse(null);
        if (existingInvoice != null) {
            updatedInvoice.setUuid(uuid);
            return invoiceDAO.save(updatedInvoice);
        } else {
            return null; 
        }
    }

    public void deleteInvoice(UUID uuid) {
        invoiceDAO.deleteById(uuid);
    }
}
