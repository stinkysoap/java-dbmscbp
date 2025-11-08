package com.hotel.model;

import java.time.LocalDateTime;

public class Payment {
    private Long id;
    private Long invoiceId;
    private Long bookingId;
    private LocalDateTime paidAt;
    private String method;
    private double amount;
    private String reference;

    public Payment() {}

    public Payment(Long id, Long invoiceId, Long bookingId, LocalDateTime paidAt, String method, double amount, String reference) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.paidAt = paidAt;
        this.method = method;
        this.amount = amount;
        this.reference = reference;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}


