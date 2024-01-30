package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.Invoice;
import BWTEAM2.BW_final.entities.InvoiceState;
import BWTEAM2.BW_final.exception.BadRequestException;
import BWTEAM2.BW_final.payloads.client.ClientResponseDTO;
import BWTEAM2.BW_final.payloads.client.NewClientDTO;
import BWTEAM2.BW_final.payloads.invoice.InvoiceDTO;
import BWTEAM2.BW_final.payloads.invoice.InvoiceResponseDTO;
import BWTEAM2.BW_final.repositories.InvoiceDAO;
import BWTEAM2.BW_final.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceDAO invoiceDAO;

    @GetMapping
    public Page<Invoice> getInvoices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "uuid") String sort) {
        return invoiceService.getAllInvoices(page, size, sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public InvoiceResponseDTO create(@RequestBody @Validated InvoiceDTO invoice, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Invoice newInvoice = invoiceService.saveInvoice(invoice);
            return new InvoiceResponseDTO(newInvoice.getUuid());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    Invoice updateById(@PathVariable UUID id, @RequestBody InvoiceDTO body) {
        return invoiceService.updateInvoiceById(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        invoiceService.deleteInvoiceById(id);
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable UUID id) {
        return invoiceService.getInvoiceById(id);
    }



    //Filtri custom
    @GetMapping("/filters/date")
    public Page<Invoice> findByDate(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable LocalDate date){
        return invoiceService.findByDate(page, size, sort, date);
    }
    @GetMapping("/filters/invoice-state")
    public Page<Invoice> findByInvoiceState(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable InvoiceState invoiceState){
        return invoiceService.findByInvoiceState(page, size, sort, invoiceState);
    }

    @GetMapping("/filters/client")
    public Page<Invoice> findByClient(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable UUID client){
        return invoiceService.findByClient(page, size, sort, client);
    }

    @GetMapping("/filters/amount-range")
    public Page<Invoice> findByAmountRange(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable double min, @PathVariable double max){
        return invoiceService.findInvoicesByAmountRange(min, max, page, size, sort);
    }

    @GetMapping("/filters")
    public Page<Invoice> findByYear(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String sort,
                                    @PathVariable int year){
        return invoiceService.findByYear(page, size, sort, year);
    }

}
