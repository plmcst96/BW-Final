package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    ClientsService clientsService;

    @GetMapping
    public Page<Client> getClients(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "uuid") String sort) {
        return clientsService.getClients(page, size, sort);
    }
}
