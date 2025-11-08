package com.hotel.service;

import com.hotel.dao.*;
import com.hotel.dao.jdbc.*;
import com.hotel.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class BillingService {
    private final ServiceItemDao serviceDao = new JdbcServiceItemDao();
    private final BookingServiceLineDao bookingServiceLineDao = new JdbcBookingServiceLineDao();
    private final InvoiceDao invoiceDao = new JdbcInvoiceDao();
    private final PaymentDao paymentDao = new JdbcPaymentDao();
    private final BookingDao bookingDao = new JdbcBookingDao();

    public ServiceItem addService(String name, String description, double unitPrice) {
        return serviceDao.create(new ServiceItem(null, name, description, unitPrice));
    }

    public List<ServiceItem> listServices() {
        return serviceDao.findAll();
    }

    public BookingServiceLine addServiceToBooking(long bookingId, long serviceId, int quantity) {
        ServiceItem service = serviceDao.findById(serviceId).orElseThrow(() -> new IllegalArgumentException("Service not found"));
        bookingDao.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        double lineTotal = quantity * service.getUnitPrice();
        BookingServiceLine line = new BookingServiceLine(null, bookingId, serviceId, quantity, service.getUnitPrice(), lineTotal);
        return bookingServiceLineDao.create(line);
    }

    public Invoice generateInvoice(long bookingId) {
        bookingDao.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        List<BookingServiceLine> lines = bookingServiceLineDao.findByBookingId(bookingId);
        double servicesTotal = lines.stream().mapToDouble(BookingServiceLine::getLineTotal).sum();
        // Base subtotal: room total already in booking.total_amount + services
        double bookingTotal = bookingDao.findById(bookingId).get().getTotalAmount();
        double subtotal = bookingTotal + servicesTotal;
        double taxTotal = 0;      // Simplified: add tax logic later
        double discountTotal = 0; // Simplified: add discounts later
        double total = subtotal + taxTotal - discountTotal;
        Invoice invoice = new Invoice(null, bookingId, LocalDateTime.now(), subtotal, taxTotal, discountTotal, total, "ISSUED");
        return invoiceDao.create(invoice);
    }

    public Payment recordPayment(Long invoiceId, Long bookingId, String method, double amount, String reference) {
        if (invoiceId != null) invoiceDao.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        if (bookingId != null) bookingDao.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Payment payment = new Payment(null, invoiceId, bookingId, LocalDateTime.now(), method, amount, reference);
        payment = paymentDao.create(payment);
        if (invoiceId != null) {
            // naive paid status update if payments cover total
            Invoice inv = invoiceDao.findById(invoiceId).get();
            double paid = paymentDao.findByInvoiceId(invoiceId).stream().mapToDouble(Payment::getAmount).sum();
            if (paid >= inv.getTotal()) {
                inv.setStatus("PAID");
                invoiceDao.update(inv);
            }
        }
        return payment;
    }
}


