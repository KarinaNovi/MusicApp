package com.novi.app.service.impl;

import com.novi.app.model.*;
import com.novi.app.model.repository.LocationRepository;
import com.novi.app.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAllLocations() {

        return locationRepository.findAll();
    }

    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public Optional<Location> findLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Set<Group> getLocationGroups(Long locationId) {
        Optional<Location> optionalLocation = findLocationById(locationId);
        Set<Group> groups = new HashSet<>();
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            groups.addAll(location.getGroups());
        } else {
            System.out.println("WARN: No existing location with such id");
        }
        return groups;
    }

    @Override
    public Set<MusicStyle> getLocationMusicStyles(Long locationId) {
        Optional<Location> optionalLocation = findLocationById(locationId);
        Set<MusicStyle> musicStyles = new HashSet<>();
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            musicStyles.addAll(location.getMusicStyles());
        } else {
            System.out.println("WARN: No existing location with such id");
        }
        return musicStyles;
    }

    @Override
    public Set<MusicInstrument> getLocationMusicInstruments(Long locationId) {
        Optional<Location> optionalLocation = findLocationById(locationId);
        Set<MusicInstrument> musicInstruments = new HashSet<>();
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            musicInstruments.addAll(location.getMusicInstruments());
        } else {
            System.out.println("WARN: No existing location with such id");
        }
        return musicInstruments;
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void updateLocation(Long locationId, Location location) {
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        if (optionalLocation.isPresent()) {
            location.setLocationId(locationId);
            locationRepository.save(location);
        } else {
            System.out.println("WARN: No existing location with such id");
        }
    }

    @Override
    public List<Location> findLocationsWithRepetitionToday() {
        return locationRepository.findLocationsWithRepetitionToday();
    }
}