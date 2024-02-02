package BWTEAM2.BW_final.payloads.user;

import BWTEAM2.BW_final.entities.Role;

public record UserLoginResponseDTO(String token, String role) {
}
