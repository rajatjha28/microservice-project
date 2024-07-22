package com.example.user.service.controllers;

import com.example.user.service.entities.User;
import com.example.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
   // @CircuitBreaker(name ="ratingHotelBreaker",fallbackMethod = "ratingHotelFallBack")
   // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser( @PathVariable String userId) throws Throwable {
  //      logger.info("Retry count: {}", retryCount);
    //    retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

//    int retryCount = 1;
    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex){
    //    logger.info("Fallback is executed because service is down",ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUsers();
        return ResponseEntity.ok(allUser);
    }
}
