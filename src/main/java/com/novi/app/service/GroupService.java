package com.novi.app.service;

import com.novi.app.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    // all groups with current style
    // single group operation
    void saveGroup(Group group);
    Optional<Group> findGroupById(Long id);
    void deleteGroup(Long id);
    void updateGroup(Long groupId, Group group);

    // many groups operation
    List<Group> findAllGroups();
    List<Group> findNewlyCreatedGroups();
    List<Group> getGroupsWithOnDemandMembers(int quantityOnDemandMembers);
    List<Group> getGroupsWithCurrentMusicStyle(int styleId);

}
