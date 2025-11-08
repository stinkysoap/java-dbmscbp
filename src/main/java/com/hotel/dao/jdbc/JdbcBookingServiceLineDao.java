package com.hotel.dao.jdbc;

import com.hotel.dao.BookingServiceLineDao;
import com.hotel.model.BookingServiceLine;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookingServiceLineDao implements BookingServiceLineDao {
    private final ConnectionManager cm = ConnectionManager.getInstance();

    @Override
    public BookingServiceLine create(BookingServiceLine line) {
        String sql = "INSERT INTO booking_services(booking_id, service_id, quantity, unit_price, line_total) VALUES(?,?,?,?,?)";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, line.getBookingId());
            ps.setLong(2, line.getServiceId());
            ps.setInt(3, line.getQuantity());
            ps.setDouble(4, line.getUnitPrice());
            ps.setDouble(5, line.getLineTotal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) line.setId(rs.getLong(1));
            }
            return line;
        } catch (SQLException e) { throw new RuntimeException("Failed to create booking service line", e); }
    }

    @Override
    public List<BookingServiceLine> findByBookingId(Long bookingId) {
        String sql = "SELECT id, booking_id, service_id, quantity, unit_price, line_total FROM booking_services WHERE booking_id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                List<BookingServiceLine> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) { throw new RuntimeException("Failed to list booking service lines", e); }
    }

    @Override
    public boolean deleteByBookingId(Long bookingId) {
        String sql = "DELETE FROM booking_services WHERE booking_id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Failed to delete booking service lines", e); }
    }

    private BookingServiceLine map(ResultSet rs) throws SQLException {
        return new BookingServiceLine(
                rs.getLong("id"),
                rs.getLong("booking_id"),
                rs.getLong("service_id"),
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getDouble("line_total")
        );
    }
}


