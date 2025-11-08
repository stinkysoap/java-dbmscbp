package com.hotel.dao.jdbc;

import com.hotel.dao.RoomDao;
import com.hotel.model.Room;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcRoomDao implements RoomDao {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public Room create(Room room) {
        String sql = "INSERT INTO rooms(number, type, rate_per_night, status) VALUES(?,?,?,?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getNumber());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getRatePerNight());
            ps.setString(4, room.getStatus() == null ? "AVAILABLE" : room.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    room.setId(rs.getLong(1));
                }
            }
            return room;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create room", e);
        }
    }

    @Override
    public Optional<Room> findById(Long id) {
        String sql = "SELECT id, number, type, rate_per_night, status FROM rooms WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find room by id", e);
        }
    }

    @Override
    public Optional<Room> findByNumber(String number) {
        String sql = "SELECT id, number, type, rate_per_night, status FROM rooms WHERE number=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find room by number", e);
        }
    }

    @Override
    public List<Room> findAll() {
        String sql = "SELECT id, number, type, rate_per_night, status FROM rooms ORDER BY number";
        try (Connection conn = connectionManager.getConnection(); Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                List<Room> rooms = new ArrayList<>();
                while (rs.next()) {
                    rooms.add(map(rs));
                }
                return rooms;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list rooms", e);
        }
    }

    @Override
    public Room update(Room room) {
        String sql = "UPDATE rooms SET number=?, type=?, rate_per_night=?, status=? WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getNumber());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getRatePerNight());
            ps.setString(4, room.getStatus());
            ps.setLong(5, room.getId());
            ps.executeUpdate();
            return room;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update room", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (Connection conn = connectionManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete room", e);
        }
    }

    private Room map(ResultSet rs) throws SQLException {
        return new Room(
                rs.getLong("id"),
                rs.getString("number"),
                rs.getString("type"),
                rs.getDouble("rate_per_night"),
                rs.getString("status")
        );
    }
}


