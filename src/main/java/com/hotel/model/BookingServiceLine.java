package com.hotel.model;

public class BookingServiceLine {
    private Long id;
    private Long bookingId;
    private Long serviceId;
    private int quantity;
    private double unitPrice;
    private double lineTotal;

    public BookingServiceLine() {}

    public BookingServiceLine(Long id, Long bookingId, Long serviceId, int quantity, double unitPrice, double lineTotal) {
        this.id = id;
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public double getLineTotal() { return lineTotal; }
    public void setLineTotal(double lineTotal) { this.lineTotal = lineTotal; }
}


