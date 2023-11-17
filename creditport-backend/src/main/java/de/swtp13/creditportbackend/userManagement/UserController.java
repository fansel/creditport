package de.swtp13.creditportbackend.userManagement;

import de.swtp13.creditportbackend.users.User;
import de.swtp13.creditportbackend.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usermanagement")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllModules() {
        System.out.println("Get all Users");
        return userRepository.findAll();
    }
}
