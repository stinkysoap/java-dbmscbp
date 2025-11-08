package com.hotel.model;

public class Room {
    private Long id;
    private String number;
    private String type;
    private double ratePerNight;
    private String status; // AVAILABLE, OCCUPIED, MAINTENANCE

    public Room() {}

    public Room(Long id, String number, String type, double ratePerNight, String status) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.ratePerNight = ratePerNight;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(double ratePerNight) { this.ratePerNight = ratePerNight; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


