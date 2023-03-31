package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.AdsRepository;
import com.example.EventsProject.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Service
public class ApplicantsService {
    private static final Logger log = LoggerFactory.getLogger(AdsService.class);
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsService adsService;

    public ModelAndView applyToAdd(Long id, Principal principal) {
        if (principal == null) {
            return new ModelAndView("redirect:/user/register");
        } else {
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            if (user == null) {
                user = new User();
                user.setUsername("Default");
                user.setEmail("Default@gmail.com");
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
    }





        public boolean removeUserFromAd(Long adId, Integer userId, String loggedInUsername) {
            Optional<Ad> adOptional = adsRepository.findById(adId);
            if (adOptional.isPresent()) {
                Ad ad = adOptional.get();
                if (ad.getUser().getUsername().equals(loggedInUsername)) {
                    Optional<User> userOptional = userRepository.findById(userId);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        if (ad.getApplicants().contains(user)) {
                            ad.getApplicants().remove(user);
                            adsRepository.save(ad);
                            log.info("User {} was removed from ad {}", user.getUsername(), ad.getId());
                            return true;
                        } else {
                            log.warn("User {} is not an applicant for ad {}", user.getUsername(), ad.getId());
                        }
                    } else {
                        log.warn("User with id {} not found", userId);
                    }
                } else {
                    log.warn("User {} is not authorized to remove users from ad {}", loggedInUsername, adId);
                }
            } else {
                log.warn("Ad with id {} not found", adId);
            }
            return false;
        }
    }


