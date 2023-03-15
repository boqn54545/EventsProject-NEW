package com.example.EventsProject.Controllers;

import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("user", new User());

        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(Model model, @Valid User user , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("successMessage", "User not registered!");
            return "register";
        }
        List<Object> userPresentObj = userService.isUserPresent(user);
        if((Boolean) userPresentObj.get(0)){
            model.addAttribute("successMessage", userPresentObj.get(1));
            return "register";
        }
        userService.saveUser(user);

        return "login";
    }

}
