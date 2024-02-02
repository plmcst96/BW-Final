package BWTEAM2.BW_final.controllers;

import BWTEAM2.BW_final.entities.Role;
import BWTEAM2.BW_final.entities.User;
import BWTEAM2.BW_final.exception.BadRequestException;
import BWTEAM2.BW_final.payloads.user.UserDTO;
import BWTEAM2.BW_final.payloads.user.UserLoginDTO;
import BWTEAM2.BW_final.payloads.user.UserLoginResponseDTO;
import BWTEAM2.BW_final.payloads.user.UserResponseDTO;
import BWTEAM2.BW_final.services.AuthService;
import BWTEAM2.BW_final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        Map<String, String> authResponse = authService.authenticateUser(body);
        return new UserLoginResponseDTO(authResponse.get("token"), authResponse.get("role"));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException("Ci sono errori nel payload!");
        }else {
            User newUser = authService.registerUser(newUserPayload);

            return new UserResponseDTO(newUser.getUuid());
        }
    }

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createAdmin(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException("Ci sono errori nel payload!");
        }else {
            User newUser = authService.registerAdmin(newUserPayload);

            return new UserResponseDTO(newUser.getUuid());
        }
    }
}
