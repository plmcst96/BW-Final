package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.config.EmailSender;
import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.exception.BadRequestException;
import BWTEAM2.BW_final.payloads.client.ClientResponseDTO;
import BWTEAM2.BW_final.payloads.client.NewClientDTO;
import BWTEAM2.BW_final.payloads.email.EmailDTO;
import BWTEAM2.BW_final.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    ClientsService clientsService;
    @Autowired
    EmailSender emailSender;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClientResponseDTO create(@RequestBody @Validated NewClientDTO device, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Client newDevice = clientsService.save(device);
            return new ClientResponseDTO(newDevice.getUuid());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    Client updateById(@PathVariable UUID id, @RequestBody NewClientDTO body) {
        return clientsService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable UUID id) {
        clientsService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable UUID id) {
        return clientsService.findById(id);
    }

    @GetMapping
    public Page<Client> getClients(
            @RequestParam(required = false) Double minRevenue,
            @RequestParam(required = false) Double maxRevenue,
            @RequestParam(required = false, value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inputDate,
            @RequestParam(required = false, value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastContactDate,
            @RequestParam(required = false) String businessName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid") String sort) {

        return clientsService.getClientsByParams(minRevenue, maxRevenue, inputDate, lastContactDate, businessName, page,size,sort);
    }

    @PostMapping("/{id}/logo")
    public String uploadExample(@PathVariable UUID id, @RequestParam("logo") MultipartFile body) throws IOException {
        return clientsService.uploadPicture(id, body);
    }

    @PostMapping("/sendmail/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void sendMail(@PathVariable UUID id, @RequestBody EmailDTO emailDTO) {
         Client client = clientsService.findById(id);
        emailSender.sendRegistrationEmail(client, emailDTO);
    }
}
