package com.example.user.service.services.impl;

import com.example.user.service.entities.Hotel;
import com.example.user.service.entities.Rating;
import com.example.user.service.entities.User;
import com.example.user.service.exceptions.ResourceNotFoundException;
import com.example.user.service.external.services.HotelService;
import com.example.user.service.repositories.UserRepository;
import com.example.user.service.services.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return (User) userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @SneakyThrows
    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID is not found on server :: " + userId));
        Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}",ratingsOfUsers);

        List<Rating> ratings = Arrays.stream(ratingsOfUsers).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
                /*ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                Hotel hotel = forEntity.getBody();
                logger.info("Response status code: {}",forEntity.getStatusCode());*/
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);
                return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

 /*   @Override
    public User deleteUser(String userId) {
        return null;
    }

    @Override
    public User updateUser(String userId) {
        return null;
    }*/
}
