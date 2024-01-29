package BWTEAM2.BW_final.payloads;

import BWTEAM2.BW_final.entities.ClientType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewClientDTO(
        @NotEmpty(message = "Business name cannot be empty.")
        @Size(min = 3, max = 30, message = "The business name must have a minimum of 3 characters and a maximum of 30.")
        String businessName,
        @NotEmpty(message = "VAT number cannot be empty.")
        @Size(min = 11, max = 11, message = "The VAT number must have a minimum of 11 characters")
        String VATNumber,
        @NotEmpty(message = "Email cannot be empty.")
        @Email(message = "Insert a valid email")
        String email,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format must be: yyyy-mm-dd")
        String inputDate,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format must be: yyyy-mm-dd")
        String lastContactDate,
        @NotBlank
        long annualRevenue,
        @NotEmpty(message = "Email cannot be empty.")

        @Email(message = "Insert a valid email")

        String pec,
        @Pattern(regexp = "^[0-9]{10}$")
        @NotEmpty(message = "Telephone contact number cannot be empty")
        String telephoneNumberContact,
        @Pattern(regexp = "^[0-9]{10}$")
        @NotEmpty(message = "Telephone number cannot be empty")
        String telephone,
        @NotEmpty(message = "Email cannot be empty.")
        @Email(message = "Insert a valid email")
        String contactEmail,
        @NotEmpty(message = "Name cannot be empty.")
        @Size(min = 3, max = 20, message = "The name must have a minimum of 3 characters and a maximum of 20.")
        String name,
        @NotEmpty(message = "Surname cannot be empty.")
        @Size(min = 3, max = 20, message = "The surname must have a minimum of 3 characters and a maximum of 20.")
        String surname,
        @NotEmpty(message = "Surname cannot be empty.")
        String clientType
) {
}
