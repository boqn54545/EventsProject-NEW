package com.example.EventsProject.Controllers;
import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.InterestsEnum;
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
    public String searchAds(Model model,
                            @RequestParam(required=false) String title,
                            @RequestParam(required=false) String city,
                            @RequestParam(required=false) InterestsEnum interest
    ) {
        List<Ad> ads = adsService.searchAds(title, city, interest);
        model.addAttribute("ads", ads);
        return "index";
    }

    @PostMapping("submit")
    private ModelAndView saveAd(@Valid Ad ad, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/createAd");
        } else {
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            String warningMessage = adsService.saveAd(ad,user);
            if (warningMessage != null) {
                model.addAttribute("warning", warningMessage);
                model.addAttribute("ad", ad);
                return new ModelAndView("/createAd");
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
    public ModelAndView deleteAd(@PathVariable(name = "id") Long id) {
        adsRepository.deleteById(id);
        return new ModelAndView("redirect:/event");
    }

    @PostMapping("/edit/{id}")
    public String editAd(@PathVariable(name = "id") Long id, Model m){
        m.addAttribute("ad",adsRepository.findById(id));
        return "/edit";
    }

}