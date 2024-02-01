package BWTEAM2.BW_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String street;
    @Column(name = "street_number")
    private int streetNumber;
    private String district;
    @Column(name = "zip_code")
    private int zipCode;
    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;
    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

}
