package com.example.EventsProject.Services;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email) == null;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

        public void editUser (User user){
            Optional<User> optionalUser = userRepository.findById(Math.toIntExact(user.getId()));
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
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
                if (user.getUsername() != null) {
                    existingUser.setUsername(user.getUsername());
                }
                if (user.getEmail() != null) {
                    existingUser.setEmail(user.getEmail());
                }

                existingUser.setBirthDay(user.getBirthDay());

                userRepository.save(existingUser);
            }
        }
    }