package BWTEAM2.BW_final.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Invoice {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private LocalDate date;
    private double amount;
    private int number;
    @Enumerated(EnumType.STRING)
    private InvoiceState invoiceState;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
