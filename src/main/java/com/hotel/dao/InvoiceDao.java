package com.hotel.dao;

import com.hotel.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceDao {
    Invoice create(Invoice invoice);
    Optional<Invoice> findById(Long id);
    Optional<Invoice> findByBookingId(Long bookingId);
    List<Invoice> findAll();
    Invoice update(Invoice invoice);
    boolean delete(Long id);
}


