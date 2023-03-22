package com.example.EventsProject.Controllers;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import com.example.EventsProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public String showUsers(Model m) {
        Iterable<User> users = userRepository.findAll();
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("saveUser")
    private ModelAndView saveUser(@Valid User user, BindingResult bindingResult, Model m) {
        if (bindingResult.hasErrors()) {
            m.addAttribute("user", user);
            return new ModelAndView("redirect:/");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ModelAndView("redirect:/login");
        }
    }

    @PostMapping("/edit/{id}")
    public String editUserById(@PathVariable(name = "id") Integer id, Model m) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            m.addAttribute("user", user);
            return "editUsers";
        } else {
            return "editUsers";
        }
    }

    @PostMapping("/banUser/{id}")
    public ModelAndView banUser(@PathVariable(name = "id") Integer id) {
        userRepository.deleteById(id);
        return new ModelAndView("redirect:/user");
    }
    @PostMapping("/editUser")
    private ModelAndView editUser(@Valid User user, BindingResult bindingResult, Model m) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());
            });
            m.addAttribute("user", user);
            return new ModelAndView("editUsers");
        }
        userService.editUser(user);
        return new ModelAndView("redirect:/user");
    }


}

