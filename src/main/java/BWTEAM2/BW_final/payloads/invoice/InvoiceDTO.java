package BWTEAM2.BW_final.payloads.invoice;
import BWTEAM2.BW_final.entities.InvoiceState;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceDTO(@NotNull(message = "Date cannot be null")
                         @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date not valid (yyyy-mm-dd)")
                         String date,
                         @NotNull(message = "Amount cannot be null")
                         @Max(value = 100000, message = "The amount cannot be valid")
                         double amount,
                         @NotNull(message = "Number invoice cannot be null")
                         @Max(value = 10000, message = "The number cannot be valid")
                         int number,

                         @NotNull(message = "Invoice state cannot be null")
                         @NotEmpty(message = "Invoice state cannot be empty")
                         @Size(min = 3, max = 20, message = "password must be between 3 e 20 chars")
                         String invoiceState,
                         @NotNull(message = "Client ID cannot be null")
                         UUID clientId
) {}

