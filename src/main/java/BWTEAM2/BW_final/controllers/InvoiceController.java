package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.entities.InvoiceState;
import BWTEAM2.BW_final.payloads.invoice.InvoiceDTO;
import BWTEAM2.BW_final.repositories.InvoiceDAO;
import BWTEAM2.BW_final.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    //Filtri custom
    @GetMapping("/filters")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Invoice> findByDate(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable LocalDate date){
        return invoiceService.findByDate(page, size, sort, date);
    }
    @GetMapping("/filters")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Invoice> findByInvoiceState(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable InvoiceState invoiceState){
        return invoiceService.findByInvoiceState(page, size, sort, invoiceState);
    }

    @GetMapping("/filters")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Invoice> findByClient(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable UUID client){
        return invoiceService.findByClient(page, size, sort, client);
    }

    @GetMapping("/filters")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Invoice> findByAmountRange(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable double min, @PathVariable double max){
        return invoiceService.findInvoicesByAmountRange(min, max, page, size, sort);
    }

    @GetMapping("/filters")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Page<Invoice> findByYear(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable int year){
        return invoiceService.findByYear(page, size, sort, year);
    }


}
