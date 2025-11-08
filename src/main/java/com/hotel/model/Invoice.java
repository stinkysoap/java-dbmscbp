package com.hotel.model;

import java.time.LocalDateTime;

public class Invoice {
    private Long id;
    private Long bookingId;
    private LocalDateTime issuedAt;
    private double subtotal;
    private double taxTotal;
    private double discountTotal;
    private double total;
    private String status; // ISSUED, PAID, VOID

    public Invoice() {}

    public Invoice(Long id, Long bookingId, LocalDateTime issuedAt, double subtotal, double taxTotal, double discountTotal, double total, String status) {
        this.id = id;
        this.bookingId = bookingId;
        this.issuedAt = issuedAt;
        this.subtotal = subtotal;
        this.taxTotal = taxTotal;
        this.discountTotal = discountTotal;
        this.total = total;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public double getTaxTotal() { return taxTotal; }
    public void setTaxTotal(double taxTotal) { this.taxTotal = taxTotal; }
    public double getDiscountTotal() { return discountTotal; }
    public void setDiscountTotal(double discountTotal) { this.discountTotal = discountTotal; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


