package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.InterestsEnum;
import com.example.EventsProject.Repositories.AdsRepository;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class AdsService {
     @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsRepository adsRepository;


    public List<Ad> searchAds(String title, String city, InterestsEnum interest) {
        Ad searchCriteria = new Ad();
        if (title != null && !title.isEmpty()) {
            searchCriteria.setTitle(title);
        }
        if (city != null && !city.isEmpty()) {
            searchCriteria.setCity(city);
        }
        if (interest != null) {
            searchCriteria.setInterest(interest);
        }


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Ad> example = Example.of(searchCriteria, matcher);

        return adsRepository.findAll(example);
    }

    public String saveAd(Ad ad, User user) {
        try {
            if (ad.getMinAge() != null && ad.getMaxAge() != null && ad.getMinAge() > ad.getMaxAge()) {
                throw new IllegalArgumentException("Minimum age cannot be greater than maximum age");
            }

            if (ad.getMinPrice() != null && ad.getMaxPrice() != null && ad.getMinPrice() > ad.getMaxPrice()) {
                throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
            }

            ad.setUser(user);
            adsRepository.save(ad);

            return null;
        } catch (IllegalArgumentException e) {
            return e.getMessage();

        }
    }
       public ModelAndView SubmitAd(@Valid Ad ad, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/createAds");
        } else {
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            String warningMessage = saveAd(ad,user);
            if (warningMessage != null) {
                model.addAttribute("warning", warningMessage);
                model.addAttribute("ad", ad);
                return new ModelAndView("/createAds");
            } else {
                return new ModelAndView("redirect:/event");
            }
        }}
    public ResponseEntity<String> applyAd(Ad ad, User user) {
        try {
            Set<User> applicants = ad.getApplicants();
            for (User applicant : applicants) {
                if (applicant.getUsername().equals(user.getUsername()) && applicant.getEmail().equals(user.getEmail())) {
                    throw new IllegalStateException(String.format("User %s has already applied to ad %d", user.getUsername(), ad.getId()));
                }
            }
            applicants.add(user);
            ad.setApplicants(applicants);
            adsRepository.save(ad);
            return ResponseEntity.ok("Application submitted successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}




