package com.hotel.dao;

import com.hotel.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    Room create(Room room);
    Optional<Room> findById(Long id);
    Optional<Room> findByNumber(String number);
    List<Room> findAll();
    Room update(Room room);
    boolean delete(Long id);
}


