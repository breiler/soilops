package com.tietoevry.soilops.service;

import com.tietoevry.soilops.model.User;
import com.tietoevry.soilops.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Couldn't find user " + username))    ;
    }

    public User create(String username) {
        User user = new User();
        user.setId(null);
        user.setUsername(username);
        user.setPassword(null);
        return userRepository.save(user);
    }
}
