package com.example.EventsProject.Controllers;

import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/submit")
    private String saveStudents(@Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "sign-up-user";
        }
        userRepository.save(user);
        return "redirect:/";
    }
//    @GetMapping("/create")
//    private String createUser(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "signup_form";
//    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

}
