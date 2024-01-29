package BWTEAM2.BW_final.services;


import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.ClientType;
import BWTEAM2.BW_final.payloads.NewClientDTO;
import BWTEAM2.BW_final.repositories.ClientsDAO;
import jakarta.persistence.Column;
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

    public Client save(NewClientDTO body) {
        Client client = new Client();
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

        return clientsDAO.save(client);
    }

    public List<Client> findAll() {
        return clientsDAO.findAll();
    }


    public Page<Client> getClients(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return clientsDAO.findAll(pageable);
    }


/*
    public Client findById(UUID id) {
        return clientsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
*/

/*    public Client findByIdAndUpdate(UUID uuid, Client body) {
        Client found = this.findById(uuid);
        found.setBusinessName(body.getBusinessName());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setClientType(body.getClientType());
        found.setEmail(body.getEmail());
        return clientsDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        Client found = this.findById(uuid);
        clientsDAO.delete(found);
    }*/
/*    public Client findByEmail(String email) throws NotFoundException
    {
        System.out.println(clientsDAO.findByEmail(email));
        return clientsDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Client with email " + email + " not found!"));
    }*/


}
