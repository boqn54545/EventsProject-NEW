package com.example.EventsProject.ServiceTests;

import com.example.EventsProject.Entities.Ad;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Enums.InterestsEnum;
import com.example.EventsProject.Repositories.AdsRepository;
import com.example.EventsProject.Services.AdsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AdsServiceTest {

    @Mock
    private AdsRepository adsRepository;

    @InjectMocks
    private AdsService adsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchAds() {
        String title = "Ad";
        String city = "Razgrad";
        InterestsEnum interest = InterestsEnum.MUSIC;
        Ad searchCriteria = new Ad();
        searchCriteria.setTitle(title);
        searchCriteria.setCity(city);
        searchCriteria.setInterest(interest);

        List<Ad> expectedAds = Collections.singletonList(new Ad());
        when(adsRepository.findAll(any(Example.class))).thenReturn(expectedAds);

        List<Ad> actualAds = adsService.searchAds(title, city, interest);

        assertEquals(expectedAds, actualAds);
    }

    @Test
    void testSaveAd() {

        User user = new User();
        Ad ad = new Ad();
        ad.setTitle("Ad");
        ad.setCity("Razgrad");
        ad.setMinAge(18);
        ad.setMaxAge(30);
        ad.setMinPrice(10);
        ad.setMaxPrice(20);
        ad.setInterest(InterestsEnum.MUSIC);
        when(adsRepository.save(ad)).thenReturn(ad);


        String ErrorMessage = adsService.saveAd(ad, user);

        assertNull(ErrorMessage);
    }

    @Test
    void testSaveAdWhenInvalidMinAge() {

        User user = new User();
        Ad ad = new Ad();
        ad.setTitle("Ad");
        ad.setCity("Razgrad");
        ad.setMinAge(30);
        ad.setMaxAge(18);


        String actualErrorMessage = adsService.saveAd(ad, user);


        assertEquals("Minimum age can't be higher than maximum age", actualErrorMessage);
    }

    @Test
    void testSaveAdWhenInvalidMinPrice() {
        User user = new User();
        Ad ad = new Ad();
        ad.setTitle("Ad");
        ad.setCity("Razgrad");
        ad.setMinPrice(20);
        ad.setMaxPrice(10);

        String actualErrorMessage = adsService.saveAd(ad, user);

        assertEquals("Minimum price can't be higher maximum price", actualErrorMessage);
    }


        @Captor
        ArgumentCaptor<Ad> adCaptor;

        @Test
        void testApplyAd() {

            User user = new User();
            user.setUsername("Test User");
            user.setEmail("test@example.com");
            Ad ad = new Ad();
            ad.setTitle("Test Ad");
            ad.setCity("Test City");
            Set<User> applicants = new HashSet<>();
            ad.setApplicants(applicants);

            when(adsRepository.save(any(Ad.class))).thenReturn(ad);

            assertDoesNotThrow(() -> adsService.applyAd(ad, user));

            verify(adsRepository).save(adCaptor.capture());
            Ad savedAd = adCaptor.getValue();

            assertEquals(1, savedAd.getApplicants().size());
            assertTrue(savedAd.getApplicants().contains(user));
        }
}
