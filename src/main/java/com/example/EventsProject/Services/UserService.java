package com.example.EventsProject.Services;

import com.example.EventsProject.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    public List<Object> isUserPresent(User user) {
   return (List<Object>) user; }

    public void saveUser(User user) {
    }
}
