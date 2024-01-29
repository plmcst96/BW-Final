package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.exception.NotFoundException;
import BWTEAM2.BW_final.payloads.invoice.InvoiceDTO;
import BWTEAM2.BW_final.repositories.InvoiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    InvoiceDAO invoiceDAO;


    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }

    public Invoice getInvoiceById(UUID uuid) {
        return invoiceDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public Invoice saveInvoice(InvoiceDTO body) {
        Invoice invoice = new Invoice();
        invoice.setDate(body.getDate());
        invoice.setAmount(body.getAmount());
        invoice.setNumber(body.getNumber());
        invoice.setInvoiceState(body.getInvoiceState());
        Client client = clientService.getClientById(body.getClientId());
        invoice.setClient(client);
        return invoiceDAO.save(invoice);
    }

    public Page<Invoice> findAllInvoices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return invoiceDAO.findAll(pageable);
    }

    public Invoice updateInvoiceById(UUID uuid, InvoiceDTO body) {
        Invoice invoice = invoiceDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        invoice.setDate(body.getDate());
        invoice.setAmount(body.getAmount());
        invoice.setNumber(body.getNumber());
        invoice.setInvoiceState(body.getInvoiceState());
        Client client = clientService.getClientById(body.getClientId());
        invoice.setClient(client);
        return invoiceDAO.save(invoice);
    }

    public void deleteInvoiceById(UUID uuid) {
        Invoice invoice = invoiceDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        invoiceDAO.delete(invoice);
    }
}
