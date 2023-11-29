package com.novi.app.service.testData;

import com.novi.app.model.Group;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class TestGroup {

    public static Group createSimpleGroup() {
        String groupName = "Test group";
        int maxQuantity = 6;
        int currentQuantity = 5;
        String description = "Test ability";
        Date repetitionDtm = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        String password = "12345";
        long leaderId = 126;
        return new Group(groupName, maxQuantity, currentQuantity, description, repetitionDtm, password, leaderId);
    }

    public static Group updateSimpleGroup(long groupId) {
        Group group = createSimpleGroup();
        group.setGroupId(groupId);
        group.setDescription("Updated group");
        return group;
    }
}
