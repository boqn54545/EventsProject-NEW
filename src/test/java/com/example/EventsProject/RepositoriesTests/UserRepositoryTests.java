package com.example.EventsProject.RepositoriesTests;

import com.example.EventsProject.Controllers.UserController;

import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import com.example.EventsProject.Services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {


    @Autowired
    private TestEntityManager entityManager;
    @Mock
    private Principal principal;
    @Mock
    private Model m;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserController userController;




    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setPassword("test1234");
        user.setUsername("test1");
        user.setFirstName("test1");
        user.setSecondName("secName");
        user.setLastName("lastName");
        user.setDescription("Very HANDSOME!!");

        User savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        Assertions.assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
    @Test
    public void testEditUser() {
        User user = new User();
        user.setId(40);
        user.setEmail("test1@gmail.com");
        user.setPassword("test1234");
        user.setUsername("test1");
        user.setFirstName("test1");
        user.setSecondName("secNam");
        user.setLastName("lastName");
        user.setDescription("Very HANDSOME!!");


         ModelAndView editedUser = userController.editUserById(1,m,principal,user.getUsername());
       assertEquals(null,editedUser);
    }

        @Test
        public void testBanUser() {
            User user = new User();
            user.setId(100);
            userRepository.save(user);

            ModelAndView mav = userController.banUser(100);

            assertEquals("redirect:/user", mav.getViewName());
        }

    }


