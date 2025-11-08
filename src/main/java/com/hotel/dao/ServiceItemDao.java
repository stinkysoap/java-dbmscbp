package com.hotel.dao;

import com.hotel.model.ServiceItem;

import java.util.List;
import java.util.Optional;

public interface ServiceItemDao {
    ServiceItem create(ServiceItem service);
    Optional<ServiceItem> findById(Long id);
    Optional<ServiceItem> findByName(String name);
    List<ServiceItem> findAll();
    ServiceItem update(ServiceItem service);
    boolean delete(Long id);
}


