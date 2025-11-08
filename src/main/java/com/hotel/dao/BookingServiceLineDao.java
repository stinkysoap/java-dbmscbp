package com.hotel.dao;

import com.hotel.model.BookingServiceLine;

import java.util.List;

public interface BookingServiceLineDao {
    BookingServiceLine create(BookingServiceLine line);
    List<BookingServiceLine> findByBookingId(Long bookingId);
    boolean deleteByBookingId(Long bookingId);
}


