package com.mylearnings.user.service.services;

import com.mylearnings.user.service.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    List<User> getUsers();
    User getUser(String userId);
}
