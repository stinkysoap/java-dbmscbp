package com.hotel.dao;

import com.hotel.model.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingDao {
    Booking create(Booking booking);
    Optional<Booking> findById(Long id);
    List<Booking> findAll();
    List<Booking> findActiveByRoom(Long roomId);
    boolean overlaps(Long roomId, LocalDate start, LocalDate end);
    Booking update(Booking booking);
    boolean delete(Long id);
}


