package com.example.EventsProject.ServiceTests;

import com.example.EventsProject.Controllers.UserController;
import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import com.example.EventsProject.Services.AuthenticationService;
import com.example.EventsProject.Services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserController userController;

    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Mock
    private UserService userService;
    @Mock
    private TestEntityManager entityManager;
    @Mock
    private ModelAndView modelAndView;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setPassword("test1234");
        user.setUsername("test1");
        user.setFirstName("test1");
        user.setSecondName("secName");
        user.setLastName("lastName");
        user.setDescription("Very HANDSOME!!");

        userRepository.save(user);
        ModelAndView ErrorMessage = userService.saveUser(user);

        assertNull(ErrorMessage);
    }

    @Test
    public void testEditUserService() {
        User user = new User();
        user.setId(1);
        user.setEmail("test1@gmail.com");
        user.setPassword("test1234");
        user.setUsername("test1");
        user.setFirstName("test1");
        user.setSecondName("secNam");
        user.setLastName("lastName");
        user.setDescription("Very HANDSOME!!");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.editUserService(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testEditUserByIdService() {
        User user = new User();
        user.setId(1);
        user.setEmail("test1@gmail.com");
        user.setPassword("test1234");
        user.setUsername("test1");
        user.setFirstName("test1");
        user.setSecondName("secNam");
        user.setLastName("lastName");
        user.setDescription("Very HANDSOME!!");

        when(principal.getName()).thenReturn(user.getUsername());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        modelAndView = userService.editUserByIdService(user.getId(), model, principal, user.getUsername());
        assertEquals("/editUsers", modelAndView.getViewName());
        assertEquals(user, modelAndView.getModel().get("user"));
    }


    @Test
    public void testBanUser() {
        User user = new User();
        user.setId(100);
        userRepository.save(user);

        ModelAndView mav = userController.banUser(user.getId());

        assertNotNull(mav);
        assertEquals("redirect:/user", mav.getViewName());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertTrue(deletedUser.isEmpty());
    }
}