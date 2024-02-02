package BWTEAM2.BW_final.config;

import BWTEAM2.BW_final.entities.Client;
import BWTEAM2.BW_final.entities.User;
import BWTEAM2.BW_final.payloads.email.EmailDTO;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private String mailgunApiKey;
    private String mailgunDomainname;

    @Value("${mailgun.maildefault}")
    private String mailFrom;

    public EmailSender(@Value("${mailgun.apikey}") String mailgunApiKey,
                       @Value("${mailgun.domainname}") String mailgunDomainName) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainname = mailgunDomainName;
    }


    public void sendRegistrationEmail(Client recipient, EmailDTO emailDTO) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailFrom)
                .queryString("to", recipient.getEmail())
                .queryString("subject", emailDTO.subject())
                .queryString("text", emailDTO.content())
                .asJson();
        System.out.println(response);
    }
}
