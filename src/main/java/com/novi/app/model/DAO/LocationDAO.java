package com.novi.app.model.DAO;

import com.novi.app.model.Location;

import java.util.List;

public interface LocationDAO {

    Location findById(int locationId);

    void save(Location location);

    void update(int locationId, Location updateLocation);

    void deleteById(int locationId);

    List<Location> findAll();
}
