package com.hotel.dao.jdbc;

import com.hotel.dao.GuestDao;
import com.hotel.model.Guest;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGuestDao implements GuestDao {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public Guest create(Guest guest) {
        String sql = "INSERT INTO guests(full_name, phone, email) VALUES(?,?,?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getFullName());
            ps.setString(2, guest.getPhone());
            ps.setString(3, guest.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    guest.setId(rs.getLong(1));
                }
            }
            return guest;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create guest", e);
        }
    }

    @Override
    public Optional<Guest> findById(Long id) {
        String sql = "SELECT id, full_name, phone, email FROM guests WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find guest by id", e);
        }
    }

    @Override
    public List<Guest> findAll() {
        String sql = "SELECT id, full_name, phone, email FROM guests ORDER BY full_name";
        try (Connection conn = connectionManager.getConnection(); Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                List<Guest> guests = new ArrayList<>();
                while (rs.next()) {
                    guests.add(map(rs));
                }
                return guests;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list guests", e);
        }
    }

    @Override
    public Guest update(Guest guest) {
        String sql = "UPDATE guests SET full_name=?, phone=?, email=? WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guest.getFullName());
            ps.setString(2, guest.getPhone());
            ps.setString(3, guest.getEmail());
            ps.setLong(4, guest.getId());
            ps.executeUpdate();
            return guest;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update guest", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM guests WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete guest", e);
        }
    }

    private Guest map(ResultSet rs) throws SQLException {
        return new Guest(
                rs.getLong("id"),
                rs.getString("full_name"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}


