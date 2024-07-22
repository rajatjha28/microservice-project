package com.example.user.service.repositories;

import com.example.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
