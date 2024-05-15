package com.novi.app.controller;

import com.novi.app.model.*;
import com.novi.app.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/locations")
@CrossOrigin
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> index(){
        List<Location> locations = locationService.findAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Location>> getLocationById(@PathVariable("id") Long locationId){
        return new ResponseEntity<>(locationService.findLocationById(locationId), HttpStatus.OK);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<Set<Group>> getLocationUsers(@PathVariable("id") Long locationId){
        return new ResponseEntity<>(locationService.getLocationGroups(locationId), HttpStatus.OK);
    }

    @GetMapping("/{id}/styles")
    public ResponseEntity<Set<MusicStyle>> getLocationMusicStyles(@PathVariable("id") Long locationId){
        return new ResponseEntity<>(locationService.getLocationMusicStyles(locationId), HttpStatus.OK);
    }

    @GetMapping("/{id}/instruments")
    public ResponseEntity<Set<MusicInstrument>> getLocationMusicInstruments(@PathVariable("id") Long locationId){
        return new ResponseEntity<>(locationService.getLocationMusicInstruments(locationId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Optional<Location>> create(@Valid @RequestBody Location location,
                                                 BindingResult bindingResult) {
        String locationName = location.getLocationName();
        double xCoordinate = location.getXCoordinate();
        double yCoordinate = location.getYCoordinate();
        String description = location.getDescription();
        double price = location.getPrice();
        Date repetitionDtm = location.getRepetitionDtm();
        if (bindingResult.hasErrors()) {
            System.out.println("Mandatory parameter is null, check request body");
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        location = new Location(locationName, xCoordinate, yCoordinate, description, price, repetitionDtm);
        locationService.saveLocation(location);
        return new ResponseEntity<>(locationService.findLocationById(location.getLocationId()), HttpStatus.CREATED);
    }

    @PostMapping("/updateLocation/{id}")
    public ResponseEntity<Optional<Location>> update(@RequestBody Location location,
                                                 @PathVariable("id") Long locationId) {
        //TODO: validate - which parameters need to be updated, which need to be taken as is
        locationService.updateLocation(locationId, location);
        return new ResponseEntity<>(locationService.findLocationById(location.getLocationId()), HttpStatus.OK);
    }

    // Not recommended physical deletion - manual corrections only!
    @DeleteMapping(value = "/deleteLocation/{id}")
    public ResponseEntity<List<Location>> deleteLocation(@PathVariable("id") Long locationId) {
        locationService.deleteLocation(locationId);
        List<Location> locations = locationService.findAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    /**
     * Many locations operations
     */
    @GetMapping("/specificLocations")
    public ResponseEntity<List<Location>> getLocationsWithRepetitionToday() {
        return new ResponseEntity<>(locationService.findLocationsWithRepetitionToday(), HttpStatus.OK);
    }
}



