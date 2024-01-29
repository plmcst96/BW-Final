package BWTEAM2.BW_final.payloads.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorPayloadWithListDTO extends ErrorPayload{

    List<String> errorsList;

    public ErrorPayloadWithListDTO(String message, Date date, List<String> errorsList) {
        super(message, date);
        this.errorsList = errorsList;
    }

}
