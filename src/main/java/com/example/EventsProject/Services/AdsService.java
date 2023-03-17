package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.InterestsEnum;
import com.example.EventsProject.Repositories.AdsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AdsService {

    @Autowired
    private AdsRepository adsRepository;
    private static final Logger log = LoggerFactory.getLogger(AdsService.class);

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

    public void applyAd(Ad ad, User user) throws IllegalStateException {
        Set<User> applicants = ad.getApplicants();

        for (User applicant : applicants) {
            if (applicant.getUsername().equals(user.getUsername()) && applicant.getEmail().equals(user.getEmail())) {
                throw new IllegalStateException(String.format("User %s has already applied to ad %d", user.getUsername(), ad.getId()));
            }
        }
        applicants.add(user);
        ad.setApplicants(applicants);
        adsRepository.save(ad);
    }


}

