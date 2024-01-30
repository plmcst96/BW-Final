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
    @Column(name = "municipal_serial_num")
    private String municipalSerialNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "province_code")
    private String provinceCode;

   @ManyToOne
   @JoinColumn(name = "province_name")
   private Province province;

    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Address> addresses;


}
