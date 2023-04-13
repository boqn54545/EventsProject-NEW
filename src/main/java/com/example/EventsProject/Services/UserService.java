package com.example.EventsProject.Services;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.security.Principal;
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

    public ModelAndView saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return null;
    }

    public ModelAndView editUserByIdService(@PathVariable(name = "id") Integer id, Model m, Principal principal, @RequestParam(name = "name", required = false) String name) {
        String loggedInUsername = principal.getName();
        Optional<User> userOptional;
        if (name != null) {
            userOptional = Optional.ofNullable(userRepository.findByUsername(name));
        } else {
            userOptional = userRepository.findById(id);
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getUsername().equals(loggedInUsername)) {
                return new ModelAndView("redirect:/user");
            }
            m.addAttribute("user", user);
            return new ModelAndView("/editUsers") ;
        } else {
            return new ModelAndView("/editUsers") ;
        }
    }

        public void editUserService (User user){
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