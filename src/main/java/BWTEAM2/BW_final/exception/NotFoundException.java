package BWTEAM2.BW_final.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends RuntimeException{
    public NotFoundException(UUID id) {
        super("id " + id + " not found!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
