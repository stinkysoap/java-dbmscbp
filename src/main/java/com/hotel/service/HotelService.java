package com.hotel.service;

import com.hotel.dao.BookingDao;
import com.hotel.dao.GuestDao;
import com.hotel.dao.RoomDao;
import com.hotel.dao.jdbc.JdbcBookingDao;
import com.hotel.dao.jdbc.JdbcGuestDao;
import com.hotel.dao.jdbc.JdbcRoomDao;
import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HotelService {
    private final RoomDao roomDao = new JdbcRoomDao();
    private final GuestDao guestDao = new JdbcGuestDao();
    private final BookingDao bookingDao = new JdbcBookingDao();

    public Room addRoom(String number, String type, double ratePerNight) {
        Room room = new Room(null, number, type, ratePerNight, "AVAILABLE");
        return roomDao.create(room);
    }

    public List<Room> listRooms() {
        return roomDao.findAll();
    }

    public Guest addGuest(String fullName, String phone, String email) {
        Guest guest = new Guest(null, fullName, phone, email);
        return guestDao.create(guest);
    }

    public List<Guest> listGuests() {
        return guestDao.findAll();
    }

    public Booking createBooking(long roomId, long guestId, LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
        if (bookingDao.overlaps(roomId, checkIn, checkOut)) {
            throw new IllegalStateException("Room is not available for the selected dates");
        }
        Room room = roomDao.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = nights * room.getRatePerNight();
        Booking booking = new Booking(null, roomId, guestId, checkIn, checkOut, total, "BOOKED");
        return bookingDao.create(booking);
    }

    public List<Booking> listBookings() {
        return bookingDao.findAll();
    }
}


