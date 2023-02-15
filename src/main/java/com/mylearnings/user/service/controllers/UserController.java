package com.mylearnings.user.service.controllers;

import com.mylearnings.user.service.entities.User;
import com.mylearnings.user.service.services.UserService;
import com.mylearnings.user.service.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelFallback",fallbackMethod = "ratingHotelFallback")
    public  ResponseEntity<User> getUser(@PathVariable String userId){
        User requestedUser=userService.getUser(userId);
        return ResponseEntity.ok(requestedUser);
    }

    @RequestMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers=userService.getUsers();
        return ResponseEntity.ok(allUsers);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, ExportException ex){
     logger.info("Fall back executed because  service is down: ", ex.getMessage());
     User user=User.builder()
             .email("test@email.com")
             .name("myName")
             .about("service is down")
             .userId(userId)
             .build();
     return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
