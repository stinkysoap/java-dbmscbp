package com.hotel.dao;

import com.hotel.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDao {
    Payment create(Payment payment);
    Optional<Payment> findById(Long id);
    List<Payment> findByInvoiceId(Long invoiceId);
    List<Payment> findByBookingId(Long bookingId);
    List<Payment> findAll();
    boolean delete(Long id);
}


