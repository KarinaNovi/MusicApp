package com.novi.app.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(
            LocationServiceTest.class
    );

    @Autowired
    private LocationService locationService;

    @Test
    public void testPrintAllLocationsName() {
        locationService.findAllLocations().forEach(location -> logger.info(location.getLocationName()));
    }

    @Test
    public void testGetLocationsWithRepetitionToday() {
        locationService.findLocationsWithRepetitionToday().forEach(location -> logger.info(location.getLocationName()));
    }
}
