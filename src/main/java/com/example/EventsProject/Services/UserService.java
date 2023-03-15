package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.User;
import com.example.EventsProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    @Autowired

private UserRepository userRepository;
    public List<Object> isUserPresent(User user) {
        List<Object> result = new ArrayList<>();
        List<User> userList = (List<User>) userRepository.findByEmail(user.getEmail());
        if (userList.size() > 0) {
            result.add(true);
            result.add("User with this email already exists!");
        } else {
            result.add(false);
            result.add(user);
        }
        return result;
    }

    public void saveUser(User user) {
    }
}
