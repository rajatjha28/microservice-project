package com.example.hotel.services;

import com.example.hotel.entites.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String id);
}
