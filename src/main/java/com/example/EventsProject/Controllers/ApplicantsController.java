package com.example.EventsProject.Controllers;
import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.AdsRepository;
import com.example.EventsProject.Repositories.UserRepository;
import com.example.EventsProject.Services.AdsService;
import com.example.EventsProject.Services.ApplicantsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/applicants")
public class ApplicantsController {
    private static final Logger log = LoggerFactory.getLogger(AdsController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private AdsService adsService;

    @Autowired
    private ApplicantsService applicantsService;
    @PostMapping("/apply/{id}")
    public ModelAndView applyAd(@PathVariable(name = "id") Long id, Principal principal) {
        return applicantsService.applyToAdd(id, principal);
    }

    @GetMapping("/{id}")
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

    @PostMapping("/removeUser/{adId}")
    public ModelAndView removeUser(@PathVariable(name = "adId") Long adId, @RequestParam(name = "userId") Integer userId, Principal principal) {
        String loggedInUsername = principal.getName();
        if (applicantsService.removeUserFromAd(adId, userId, loggedInUsername)) {
            return new ModelAndView("redirect:/applicants/" + adId);
        } else {
            return new ModelAndView("redirect:/applicants/" + adId);
        }
    }

}

