package BWTEAM2.BW_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "pIVA")
    private String VATNumber;
    private String email;
    @Column(name = "input_date")
    private LocalDate inputDate;
    @Column(name = "last_contact_date")
    private LocalDate lastContactDate;
    @Column(name = "annual_revenue")
    private long annualRevenue;
    private String pec;
    @Column(name = "telephone_number_contact")
    private String telephoneNumberContact;
    private String telephone;
    @Column(name = "contact_email")
    private String contactEmail;
    private String name;
    private String surname;
    private String logo;
    @Enumerated(EnumType.STRING)
    private ClientType clientType;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Invoice> invoices;
    @OneToOne(mappedBy = "client")
    private Address legalAddress;
    @OneToOne(mappedBy = "client")
    private Address operativeAddress;


}
