package com.hotel.model;

import java.time.LocalDate;

public class Booking {
    private Long id;
    private Long roomId;
    private Long guestId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalAmount;
    private String status; // BOOKED, CHECKED_IN, CHECKED_OUT, CANCELED

    public Booking() {}

    public Booking(Long id, Long roomId, Long guestId, LocalDate checkIn, LocalDate checkOut, double totalAmount, String status) {
        this.id = id;
        this.roomId = roomId;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public Long getGuestId() { return guestId; }
    public void setGuestId(Long guestId) { this.guestId = guestId; }
    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


