package com.example.EventsProject.Controllers;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import com.example.EventsProject.Services.AuthenticationService;
import com.example.EventsProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.security.Principal;




@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
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
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, Model m) {
      return authenticationService.registerUserService(user,bindingResult,m);
    }


    @PostMapping("/edit/{id}")
    public ModelAndView editUserById(@PathVariable(name = "id") Integer id, Model m, Principal principal, @RequestParam(name = "name", required = false) String name) {
        return  userService.editUserByIdService(id,m,principal,name);
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
        userService.editUserService(user);
        return new ModelAndView("redirect:/user");
    }


}

