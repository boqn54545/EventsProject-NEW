package com.example.EventsProject.Controllers;
import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.InterestsEnum;
import com.example.EventsProject.Enums.Role;
import com.example.EventsProject.Repositories.AdsRepository;
import com.example.EventsProject.Repositories.UserRepository;
import com.example.EventsProject.Services.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/event")
public class AdsController {
   private static final Logger log = LoggerFactory.getLogger(AdsController.class);

    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsService adsService;

    @GetMapping
    public String getAllAds(Model m) {
        Iterable<Ad> ads = adsRepository.findAll();
        m.addAttribute("ads", ads);
        return "index";
    }

    @GetMapping("createAds")
    public String createAd(Model m) {
        Ad ad = new Ad();
        m.addAttribute("ad", ad);
        return "/createAds";
    }
    @GetMapping("/search")
    public String searchAds(
            Model model,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) InterestsEnum interest
    ) {
        List<Ad> ads;
        if (title != null || city != null || interest != null) {
            ads = adsService.searchAds(title, city, interest);
        } else {
            ads = adsRepository.findAll();
        }
        model.addAttribute("ads", ads);
        return "index";
    }

    @PostMapping("submit")
    private ModelAndView saveAd(@Valid Ad ad, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/createAds");
        } else {
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            String warningMessage = adsService.saveAd(ad,user);
            if (warningMessage != null) {
                model.addAttribute("warning", warningMessage);
                model.addAttribute("ad", ad);
                return new ModelAndView("/createAds");
            } else {
                return new ModelAndView("redirect:/event");
            }
        }
    }
    @PostMapping("submitEdit")
    private ModelAndView saveEditAd(@Valid Ad ad, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/createAds");
        } else {
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            String warningMessage = adsService.saveAd(ad,user);
            if (warningMessage != null) {
                model.addAttribute("warning", warningMessage);
                model.addAttribute("ad", ad);
                return new ModelAndView("/edit");
            } else {
                return new ModelAndView("redirect:/event");
            }
        }
    }

    @PostMapping("/apply/{id}")
    public ModelAndView applyAd(@PathVariable(name = "id") Long id, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername("TestUsername");
            user.setEmail("Test@gmail.com");
            userRepository.save(user);
        }
        Ad ad = adsRepository.findById(id).orElse(null);
        if (ad != null) {
            adsService.applyAd(ad, user);
            log.info("User {} applied to ad {}", user.getUsername(), ad.getId());
            return new ModelAndView("redirect:/event");
        } else {
            return new ModelAndView("redirect:/event");
        }
    }


    @GetMapping("/applicants/{id}")
    public String showApplicants(@PathVariable(name = "id") Long id, Model model) {
        Ad ad = adsRepository.findById(id).orElse(null);
        if (ad != null) {
            Set<User> applicants = ad.getApplicants();
            model.addAttribute("applicants", applicants);
            model.addAttribute("ad", ad);
            return "applicants.html";
        } else {
            return "redirect:/event";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteAd(@PathVariable(name = "id") Long id, Model m, Principal principal){
        String loggedInUsername = principal.getName();
        Optional<Ad> adOptional = adsRepository.findById(id);
        if (adOptional.isPresent()) {
            Ad ad = adOptional.get();
            User adUser = ad.getUser();

            if (adUser.getRole() == null || !adUser.getRole().equals(Role.ADMIN)) {
                if (!adUser.getUsername().equals(loggedInUsername)) {
                    return "redirect:/event";
                }
            }
            adsRepository.deleteById(id);
            return "redirect:/event";
        } else {
            return "redirect:/event";
        }
    }


    @PostMapping("/edit/{id}")
    public String editAd(@PathVariable(name = "id") Long id, Model m, Principal principal){
        String loggedInUsername = principal.getName();
        Optional<Ad> adOptional = adsRepository.findById(id);
        if (adOptional.isPresent()) {
            Ad ad = adOptional.get();
            if (!ad.getUser().getUsername().equals(loggedInUsername)) {
                return "redirect:/event";
            }

            m.addAttribute("ad", ad);
            return "/edit";
        } else {
            return "redirect:/event";
        }
    }
}
