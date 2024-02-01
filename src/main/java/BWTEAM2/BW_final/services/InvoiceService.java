package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.entities.InvoiceState;
import BWTEAM2.BW_final.exception.NotFoundException;
import BWTEAM2.BW_final.payloads.invoice.InvoiceDTO;
import BWTEAM2.BW_final.repositories.InvoiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    InvoiceDAO invoiceDAO;
    @Autowired
    ClientsService clientsService;


    public Page<Invoice> getAllInvoices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return invoiceDAO.findAll(pageable);
    }

    public Invoice getInvoiceById(UUID uuid) {
        return invoiceDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public Invoice saveInvoice(InvoiceDTO body) {
        Invoice invoice = new Invoice();
        invoice.setDate(body.date());
        invoice.setAmount(body.amount());
        invoice.setNumber(body.number());
        invoice.setInvoiceState(InvoiceState.valueOf(body.invoiceState()));
        Client client = clientsService.findById(body.clientId());
        invoice.setClient(client);
        return invoiceDAO.save(invoice);
    }

    public Page<Invoice> findAllInvoices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return invoiceDAO.findAll(pageable);
    }

    public Invoice updateInvoiceById(UUID uuid, InvoiceDTO body) {
        Invoice invoice = invoiceDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        invoice.setDate(body.date());
        invoice.setAmount(body.amount());
        invoice.setNumber(body.number());
        invoice.setInvoiceState(InvoiceState.valueOf(body.invoiceState()));
        Client client = clientsService.findById(body.clientId());
        invoice.setClient(client);
        return invoiceDAO.save(invoice);
    }

    public void deleteInvoiceById(UUID uuid) {
        Invoice invoice = invoiceDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        invoiceDAO.delete(invoice);
    }

    public Page<Invoice> findInvoicesByAmountRange(double minAmount, double maxAmount, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return invoiceDAO.findInvoicesByAmountRange(minAmount, maxAmount, pageable);
    }

    public Page<Invoice> findByInvoiceState(int page, int size, String orderBy, InvoiceState invoiceState){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return invoiceDAO.findByInvoiceState(invoiceState, pageable);
    }

    public Page<Invoice> findByClient(int page, int size, String orderBy, UUID uuid){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        Client client1 = clientsService.findById(uuid);
        return invoiceDAO.findByClient(client1, pageable);
    }

    public Page<Invoice> findByDate(int page, int size, String orderBy, LocalDate date){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return  invoiceDAO.findByDate(date, pageable);
    }

    public Page<Invoice> findByYear(int page, int size, String orderBy, @RequestParam int year){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return invoiceDAO.findInvoiceByYear(year, pageable);
    }

    public Page<Invoice> findByParams(Double minAmount, Double maxAmount,InvoiceState invoiceState, String pec,
                                      int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return invoiceDAO.findByParams(minAmount, maxAmount, invoiceState, pec, pageable);

    }
}
