package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.payloads.invoice.InvoiceDTO;
import BWTEAM2.BW_final.repositories.InvoiceDAO;
import BWTEAM2.BW_final.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceDAO invoiceDAO;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, InvoiceDAO invoiceDAO) {
        this.invoiceService = invoiceService;
        this.invoiceDAO = invoiceDAO;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceDAO.findAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable UUID uuid) {
        Invoice invoice = invoiceDAO.findById(uuid).orElse(null);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDTO newInvoice) {
        Invoice savedInvoice = invoiceService.saveInvoice(newInvoice);
        return ResponseEntity.ok(savedInvoice);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable UUID uuid, @RequestBody InvoiceDTO updatedInvoice) {
        Invoice existingInvoice = invoiceDAO.findById(uuid).orElse(null);
        if (existingInvoice != null) {
            existingInvoice.setUuid(uuid);
            Invoice savedInvoice = invoiceService.saveInvoice(updatedInvoice);
            return ResponseEntity.ok(savedInvoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID uuid) {
        Invoice existingInvoice = invoiceDAO.findById(uuid).orElse(null);
        if (existingInvoice != null) {
            invoiceDAO.deleteById(uuid);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
