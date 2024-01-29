package BWTEAM2.BW_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "towns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Town {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Column(name = "province_code")
    private int provinceCode;
    @Column(name = "municipal_serial_num")
    private int municipalSerialNumber;
    private String name;
    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Address> addresses;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

}
