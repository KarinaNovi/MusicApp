package com.novi.app.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroupServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(
            GroupServiceTest.class
    );

    @Autowired
    private GroupService groupService;

    @Test
    public void testPrintAllGroupsName() {
        groupService.findAllGroups().forEach(group -> logger.info(group.getGroupName()));
    }

    @Test
    public void testGetGroupsWithOnDemandMembers() {
        groupService.getGroupsWithOnDemandMembers(1).forEach(group -> logger.info(group.getGroupName()));
    }

    @Test
    public void testGetGroupWithSpecificStyle() {
        logger.info(groupService.getGroupsWithCurrentMusicStyle(1).toString());
    }
}
