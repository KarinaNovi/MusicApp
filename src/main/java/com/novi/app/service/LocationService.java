package com.novi.app.service;

import com.novi.app.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LocationService {
    // single location operation
    // get info
    Optional<Location> findLocationById(Long locationId);
    Set<Group> getLocationGroups(Long locationId);
    Set<MusicStyle> getLocationMusicStyles(Long locationId);
    Set<MusicInstrument> getLocationMusicInstruments(Long locationId);

    // update info
    void saveLocation(Location location);
    void deleteLocation(Long locationId);
    void updateLocation(Long locationId, Location location);

    // many locations operation
    List<Location> findAllLocations();
    List<Location> findLocationsWithRepetitionToday();
}
