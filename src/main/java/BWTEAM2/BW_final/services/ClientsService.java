package BWTEAM2.BW_final.services;


import BWTEAM2.BW_final.entities.Address;
import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.ClientType;
import BWTEAM2.BW_final.exception.NotFoundException;
import BWTEAM2.BW_final.payloads.client.NewClientDTO;
import BWTEAM2.BW_final.repositories.ClientsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClientsService {

    @Autowired
    ClientsDAO clientsDAO;
    @Autowired
    AddressService addressService;


    public Client save(NewClientDTO body) {
        Client client = new Client();
        Address address1 = addressService.findById(body.legalAddress());
        Address address2 = addressService.findById(body.operativeAddress());
        client.setBusinessName(body.businessName());
        client.setVATNumber(body.VATNumber());
        client.setEmail(body.email());
        client.setInputDate(LocalDate.parse(body.inputDate()));
        client.setLastContactDate(LocalDate.parse(body.lastContactDate()));
        // qui converto la stringa che proviene dal body in un ClientType
        ClientType clientType = ClientType.valueOf(body.clientType().toUpperCase());
        client.setClientType(clientType);
        client.setAnnualRevenue(body.annualRevenue());
        client.setPec(body.pec());
        client.setTelephoneNumberContact(body.telephoneNumberContact());
        client.setTelephone(body.telephone());
        client.setName(body.name());
        client.setSurname(body.surname());
        client.setContactEmail(body.contactEmail());
        client.setLegalAddress(address1);
        client.setOperativeAddress(address2);

        return clientsDAO.save(client);
    }

    public List<Client> findAll() {
        return clientsDAO.findAll();
    }


    public Page<Client> getClients(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return clientsDAO.findAll(pageable);
    }


    public Client findById(UUID id) {
        return clientsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

   public Client findByIdAndUpdate(UUID uuid, NewClientDTO body) {
        Client found = this.findById(uuid);
        found.setBusinessName(body.businessName());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setClientType(ClientType.valueOf(body.clientType().toUpperCase()));
        found.setEmail(body.email());
        return clientsDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        Client found = this.findById(uuid);
        clientsDAO.delete(found);
    }

   public Client findByEmail(String email) throws NotFoundException
    {
        System.out.println(clientsDAO.findByEmail(email));
        return clientsDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Client with email " + email + " not found!"));
    }

    public Page<Client> getClientsByParams(Double minRevenue, Double maxRevenue, LocalDate inputDate, LocalDate lastContactDate, String businessName, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return clientsDAO.findByParams(minRevenue, maxRevenue, inputDate, lastContactDate, businessName, pageable);
    }


}
