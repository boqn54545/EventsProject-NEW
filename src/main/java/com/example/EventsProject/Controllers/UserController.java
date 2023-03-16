package com.example.EventsProject.Controllers;

import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.Role;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
     @GetMapping
     public String showUsers(Model m){
         Iterable<User> users = userRepository.findAll();
         m.addAttribute("users", users);
         return "user";
     }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user =new User();
        model.addAttribute("user", new User());

        return "register";
    }
    @PostMapping("submitUser")
    private ModelAndView saveUser(@Valid User user, BindingResult bindingResult, Model m){
        if (bindingResult.hasErrors()){
            m.addAttribute("user",user);
            return new ModelAndView("redirect:/");
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            return new ModelAndView("redirect:/login");
        }}



}
