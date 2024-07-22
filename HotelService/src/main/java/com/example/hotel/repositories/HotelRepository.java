package com.example.hotel.repositories;

import com.example.hotel.entites.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,String> {

}
