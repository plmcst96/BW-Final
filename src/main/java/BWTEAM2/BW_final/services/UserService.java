package BWTEAM2.BW_final.services;

import BWTEAM2.BW_final.entities.Role;
import BWTEAM2.BW_final.entities.User;
import BWTEAM2.BW_final.exception.NotFoundException;
import BWTEAM2.BW_final.payloads.user.UserDTO;
import BWTEAM2.BW_final.repositories.UserDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Cloudinary cloudinary;

    public Page<User> getUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userDAO.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));
    }

    public void deleteById(UUID id) {
        User user = this.findById(id);
        userDAO.delete(user);
    }

    public User findByIdAndUpdate(UUID id, UserDTO body) {
        User user = this.findById(id);
        user.setEmail(body.email());
        user.setName(body.name());
        user.setSurname(body.surname());
        return userDAO.save(user);
    }

    public User setAdmin(UUID id) {
        User user = userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        user.setRole(Role.ADMIN);
        return userDAO.save(user);
    }

    public User setUser(UUID id) {
        User user = userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        user.setRole(Role.USER);
        return userDAO.save(user);
    }

    public String uploadPicture(UUID id, MultipartFile file) throws IOException {
        User user = userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        user.setAvatar(url);
        userDAO.save(user);
        return url;
    }



}
