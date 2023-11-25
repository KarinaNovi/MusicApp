package com.novi.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void testPrintAllGroupsName() {
        groupService.findAllGroups().forEach(group -> System.out.println(group.getGroupName()));
    }

    @Test
    public void testGetGroupsWithOnDemandMembers() {
        groupService.getGroupsWithOnDemandMembers(1).forEach(group -> System.out.println(group.getGroupName()));
    }

    @Test
    public void testGetGroupWithSpecificStyle() {
        System.out.println(groupService.getGroupsWithCurrentMusicStyle(1));
    }
}
