package com.hotel.dao.jdbc;

import com.hotel.dao.ServiceItemDao;
import com.hotel.model.ServiceItem;
import com.hotel.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcServiceItemDao implements ServiceItemDao {
    private final ConnectionManager cm = ConnectionManager.getInstance();

    @Override
    public ServiceItem create(ServiceItem service) {
        String sql = "INSERT INTO services(name, description, unit_price) VALUES(?,?,?)";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getUnitPrice());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) service.setId(rs.getLong(1));
            }
            return service;
        } catch (SQLException e) { throw new RuntimeException("Failed to create service", e); }
    }

    @Override
    public Optional<ServiceItem> findById(Long id) {
        String sql = "SELECT id, name, description, unit_price FROM services WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException e) { throw new RuntimeException("Failed to find service", e); }
    }

    @Override
    public Optional<ServiceItem> findByName(String name) {
        String sql = "SELECT id, name, description, unit_price FROM services WHERE name=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        } catch (SQLException e) { throw new RuntimeException("Failed to find service by name", e); }
    }

    @Override
    public List<ServiceItem> findAll() {
        String sql = "SELECT id, name, description, unit_price FROM services ORDER BY name";
        try (Connection c = cm.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            List<ServiceItem> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException("Failed to list services", e); }
    }

    @Override
    public ServiceItem update(ServiceItem service) {
        String sql = "UPDATE services SET name=?, description=?, unit_price=? WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getUnitPrice());
            ps.setLong(4, service.getId());
            ps.executeUpdate();
            return service;
        } catch (SQLException e) { throw new RuntimeException("Failed to update service", e); }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM services WHERE id=?";
        try (Connection c = cm.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException("Failed to delete service", e); }
    }

    private ServiceItem map(ResultSet rs) throws SQLException {
        return new ServiceItem(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("unit_price")
        );
    }
}


