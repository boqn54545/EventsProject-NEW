package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;
    public ModelAndView registerUserService(@Valid User user, BindingResult bindingResult, Model m) {
        if (bindingResult.hasErrors()) {
            m.addAttribute("user", user);
            return new ModelAndView("user/register", "user", user);
        } else if (!userService.isUsernameAvailable(user.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username is already taken");
            m.addAttribute("user", user);
            return new ModelAndView("user/register", "user", user);
        } else if (!userService.isEmailAvailable(user.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email is already taken");
            m.addAttribute("user", user);
            return new ModelAndView("user/register", "user", user);
        } else {
            userService.saveUser(user);
            return new ModelAndView("redirect:/login");
        }
    }
}
