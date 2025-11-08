package com.hotel.dao.jdbc;

import com.hotel.dao.InvoiceDao;
import com.hotel.model.Invoice;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcInvoiceDao implements InvoiceDao {
    private final ConnectionManager cm = ConnectionManager.getInstance();

    @Override
    public Invoice create(Invoice invoice) {
        String sql = "INSERT INTO invoices(booking_id, issued_at, subtotal, tax_total, discount_total, total, status) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, invoice.getBookingId());
            ps.setString(2, invoice.getIssuedAt().toString());
            ps.setDouble(3, invoice.getSubtotal());
            ps.setDouble(4, invoice.getTaxTotal());
            ps.setDouble(5, invoice.getDiscountTotal());
            ps.setDouble(6, invoice.getTotal());
            ps.setString(7, invoice.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) invoice.setId(rs.getLong(1)); }
            return invoice;
        } catch (SQLException e) { throw new RuntimeException("Failed to create invoice", e); }
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        String sql = "SELECT id, booking_id, issued_at, subtotal, tax_total, discount_total, total, status FROM invoices WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException e) { throw new RuntimeException("Failed to find invoice", e); }
    }

    @Override
    public Optional<Invoice> findByBookingId(Long bookingId) {
        String sql = "SELECT id, booking_id, issued_at, subtotal, tax_total, discount_total, total, status FROM invoices WHERE booking_id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException e) { throw new RuntimeException("Failed to find invoice by booking", e); }
    }

    @Override
    public List<Invoice> findAll() {
        String sql = "SELECT id, booking_id, issued_at, subtotal, tax_total, discount_total, total, status FROM invoices ORDER BY issued_at DESC";
        try (Connection c = cm.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            List<Invoice> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("Failed to list invoices", e); }
    }

    @Override
    public Invoice update(Invoice invoice) {
        String sql = "UPDATE invoices SET booking_id=?, issued_at=?, subtotal=?, tax_total=?, discount_total=?, total=?, status=? WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, invoice.getBookingId());
            ps.setString(2, invoice.getIssuedAt().toString());
            ps.setDouble(3, invoice.getSubtotal());
            ps.setDouble(4, invoice.getTaxTotal());
            ps.setDouble(5, invoice.getDiscountTotal());
            ps.setDouble(6, invoice.getTotal());
            ps.setString(7, invoice.getStatus());
            ps.setLong(8, invoice.getId());
            ps.executeUpdate();
            return invoice;
        } catch (SQLException e) { throw new RuntimeException("Failed to update invoice", e); }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM invoices WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Failed to delete invoice", e); }
    }

    private Invoice map(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getLong("id"),
                rs.getLong("booking_id"),
                LocalDateTime.parse(rs.getString("issued_at")),
                rs.getDouble("subtotal"),
                rs.getDouble("tax_total"),
                rs.getDouble("discount_total"),
                rs.getDouble("total"),
                rs.getString("status")
        );
    }
}


