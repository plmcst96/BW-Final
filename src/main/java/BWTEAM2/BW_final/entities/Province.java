package BWTEAM2.BW_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "provinces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Province {

    @Id
    @Column(name = "province_name")
    private String name;
    @Column(name = "province_code")
    private String provinceCode;
    private String region;
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Town> towns;
}
