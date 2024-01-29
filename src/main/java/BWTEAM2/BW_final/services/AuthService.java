package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Role;
import BWTEAM2.BW_final.entities.User;
import BWTEAM2.BW_final.exception.BadRequestException;
import BWTEAM2.BW_final.exception.UnauthorizedException;
import BWTEAM2.BW_final.payloads.user.UserDTO;
import BWTEAM2.BW_final.payloads.user.UserLoginDTO;
import BWTEAM2.BW_final.repositories.UserDAO;
import BWTEAM2.BW_final.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public User registerUser(UserDTO body) throws IOException {
        userDAO.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("User with email " + a.getEmail() + " already exists");
        });
        User user = new User();
        user.setPassword(bcrypt.encode(body.password()));
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setUsername(body.username());
        user.setEmail(body.email());
        user.setRole(Role.USER);
        return userDAO.save(user);
    }
}
