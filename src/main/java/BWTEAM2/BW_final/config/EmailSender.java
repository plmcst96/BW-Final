package BWTEAM2.BW_final.config;

import BWTEAM2.BW_final.entities.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailSender {

    private String mailgunApiKey;
    private String mailgunDomainname;

    public EmailSender(@Value("${mailgun.apikey}") String mailgunApiKey,
                       @Value("${mailgun.domainname}") String mailgunDomainName) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainname = mailgunDomainName;
    }

    @Bean
    public void sendRegistrationEmail(User recipient) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", "Riccardo Di Bari <riccardodibari@hotmail.it>")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione avvenuta con successo!")
                .queryString("text", "Complimenti " + recipient.getName() + " " + recipient.getSurname() + "  per esserti registrato!")
                .asJson();
        System.out.println(response);
    }
}
