package com.hotel.dao.jdbc;

import com.hotel.dao.BookingDao;
import com.hotel.model.Booking;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcBookingDao implements BookingDao {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public Booking create(Booking booking) {
        String sql = "INSERT INTO bookings(room_id, guest_id, check_in, check_out, total_amount, status) VALUES(?,?,?,?,?,?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, booking.getRoomId());
            ps.setLong(2, booking.getGuestId());
            ps.setString(3, booking.getCheckIn().toString());
            ps.setString(4, booking.getCheckOut().toString());
            ps.setDouble(5, booking.getTotalAmount());
            ps.setString(6, booking.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    booking.setId(rs.getLong(1));
                }
            }
            return booking;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create booking", e);
        }
    }

    @Override
    public Optional<Booking> findById(Long id) {
        String sql = "SELECT id, room_id, guest_id, check_in, check_out, total_amount, status FROM bookings WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find booking by id", e);
        }
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT id, room_id, guest_id, check_in, check_out, total_amount, status FROM bookings ORDER BY check_in DESC";
        try (Connection conn = connectionManager.getConnection(); Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                List<Booking> bookings = new ArrayList<>();
                while (rs.next()) {
                    bookings.add(map(rs));
                }
                return bookings;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list bookings", e);
        }
    }

    @Override
    public List<Booking> findActiveByRoom(Long roomId) {
        String sql = "SELECT id, room_id, guest_id, check_in, check_out, total_amount, status FROM bookings WHERE room_id=? AND status IN ('BOOKED','CHECKED_IN')";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, roomId);
            List<Booking> bookings = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(map(rs));
                }
            }
            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find active bookings by room", e);
        }
    }

    @Override
    public boolean overlaps(Long roomId, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(1) FROM bookings WHERE room_id=? AND status IN ('BOOKED','CHECKED_IN') AND NOT (check_out<=? OR check_in>=?)";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, roomId);
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check overlap", e);
        }
    }

    @Override
    public Booking update(Booking booking) {
        String sql = "UPDATE bookings SET room_id=?, guest_id=?, check_in=?, check_out=?, total_amount=?, status=? WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, booking.getRoomId());
            ps.setLong(2, booking.getGuestId());
            ps.setString(3, booking.getCheckIn().toString());
            ps.setString(4, booking.getCheckOut().toString());
            ps.setDouble(5, booking.getTotalAmount());
            ps.setString(6, booking.getStatus());
            ps.setLong(7, booking.getId());
            ps.executeUpdate();
            return booking;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update booking", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete booking", e);
        }
    }

    private Booking map(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getLong("id"),
                rs.getLong("room_id"),
                rs.getLong("guest_id"),
                LocalDate.parse(rs.getString("check_in")),
                LocalDate.parse(rs.getString("check_out")),
                rs.getDouble("total_amount"),
                rs.getString("status")
        );
    }
}


