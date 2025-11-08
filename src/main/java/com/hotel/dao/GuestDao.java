package com.hotel.dao;

import com.hotel.model.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestDao {
    Guest create(Guest guest);
    Optional<Guest> findById(Long id);
    List<Guest> findAll();
    Guest update(Guest guest);
    boolean delete(Long id);
}


