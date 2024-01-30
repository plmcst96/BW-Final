package BWTEAM2.BW_final.payloads.invoice;
import BWTEAM2.BW_final.entities.InvoiceState;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceDTO(@NotNull
                         LocalDate date,
                         @NotNull
                         double amount,
                         @NotNull
                         int number,
                         @NotNull
                         String invoiceState,
                         @NotNull
                         UUID clientId
) {}

