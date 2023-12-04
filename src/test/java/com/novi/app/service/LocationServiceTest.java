package com.novi.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testPrintAllLocationsName() {
        locationService.findAllLocations().forEach(location -> System.out.println(location.getLocationName()));
    }

    @Test
    public void testGetLocationsWithRepetitionToday() {
        locationService.findLocationsWithRepetitionToday().forEach(location -> System.out.println(location.getLocationName()));
    }
}
