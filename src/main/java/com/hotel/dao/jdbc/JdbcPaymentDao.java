package com.hotel.dao.jdbc;

import com.hotel.dao.PaymentDao;
import com.hotel.model.Payment;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPaymentDao implements PaymentDao {
    private final ConnectionManager cm = ConnectionManager.getInstance();

    @Override
    public Payment create(Payment payment) {
        String sql = "INSERT INTO payments(invoice_id, booking_id, paid_at, method, amount, reference) VALUES(?,?,?,?,?,?)";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (payment.getInvoiceId() == null) ps.setNull(1, Types.INTEGER); else ps.setLong(1, payment.getInvoiceId());
            if (payment.getBookingId() == null) ps.setNull(2, Types.INTEGER); else ps.setLong(2, payment.getBookingId());
            ps.setString(3, payment.getPaidAt().toString());
            ps.setString(4, payment.getMethod());
            ps.setDouble(5, payment.getAmount());
            ps.setString(6, payment.getReference());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) payment.setId(rs.getLong(1)); }
            return payment;
        } catch (SQLException e) { throw new RuntimeException("Failed to create payment", e); }
    }

    @Override
    public Optional<Payment> findById(Long id) {
        String sql = "SELECT id, invoice_id, booking_id, paid_at, method, amount, reference FROM payments WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException e) { throw new RuntimeException("Failed to find payment", e); }
    }

    @Override
    public List<Payment> findByInvoiceId(Long invoiceId) {
        String sql = "SELECT id, invoice_id, booking_id, paid_at, method, amount, reference FROM payments WHERE invoice_id=? ORDER BY paid_at DESC";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) { return collect(rs); }
        } catch (SQLException e) { throw new RuntimeException("Failed to list payments by invoice", e); }
    }

    @Override
    public List<Payment> findByBookingId(Long bookingId) {
        String sql = "SELECT id, invoice_id, booking_id, paid_at, method, amount, reference FROM payments WHERE booking_id=? ORDER BY paid_at DESC";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) { return collect(rs); }
        } catch (SQLException e) { throw new RuntimeException("Failed to list payments by booking", e); }
    }

    @Override
    public List<Payment> findAll() {
        String sql = "SELECT id, invoice_id, booking_id, paid_at, method, amount, reference FROM payments ORDER BY paid_at DESC";
        try (Connection c = cm.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return collect(rs);
        } catch (SQLException e) { throw new RuntimeException("Failed to list payments", e); }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM payments WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Failed to delete payment", e); }
    }

    private List<Payment> collect(ResultSet rs) throws SQLException {
        List<Payment> list = new ArrayList<>();
        while (rs.next()) list.add(map(rs));
        return list;
    }

    private Payment map(ResultSet rs) throws SQLException {
        Long invoiceId = rs.getObject("invoice_id") == null ? null : rs.getLong("invoice_id");
        Long bookingId = rs.getObject("booking_id") == null ? null : rs.getLong("booking_id");
        return new Payment(
                rs.getLong("id"),
                invoiceId,
                bookingId,
                LocalDateTime.parse(rs.getString("paid_at")),
                rs.getString("method"),
                rs.getDouble("amount"),
                rs.getString("reference")
        );
    }
}


