package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void editUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setSecondName(user.getSecondName());
            existingUser.setLastName(user.getLastName());
            existingUser.setDescription(user.getDescription());

            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }

            existingUser.setEmail(user.getEmail());
            existingUser.setBirthDay(user.getBirthDay());

            userRepository.save(existingUser);
        }
    }
}
