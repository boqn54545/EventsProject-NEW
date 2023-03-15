package com.example.EventsProject.controllerTests;

import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setPassword("test1234");
        user.setUsername("test1");
        user.setFirstName("test1");
        user.setSecondName("secName");
        user.setLastName("lastName");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        Assertions.assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}
