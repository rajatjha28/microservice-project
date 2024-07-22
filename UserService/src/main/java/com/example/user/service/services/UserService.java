package com.example.user.service.services;

import com.example.user.service.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUser(String userId);

   // User deleteUser(String userId);

    //User updateUser(String userId);
}
