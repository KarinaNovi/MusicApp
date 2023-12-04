package com.novi.app.service;

import com.novi.app.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupService {
    // single group operation
    // get info
    Optional<Group> findGroupById(Long groupId);
    Set<User> getGroupMembers(Long groupId);
    Set<MusicStyle> getGroupMusicStyles(Long groupId);
    Set<MusicInstrument> getGroupMusicInstruments(Long groupId);
    Set<Location> getGroupLocations(Long groupId);
    // TODO: think about this result - maybe trigger userService for getting user's info
    Long getGroupLeaderId(Long groupId);

    // update info
    void saveGroup(Group group);
    void terminateGroup(Long groupId);
    void deleteGroup(Long groupId);
    void updateGroup(Long groupId, Group group);

    // many groups operation
    List<Group> findAllGroups();
    List<Group> findNewlyCreatedGroups();
    List<Group> findActiveGroups();
    List<Group> getGroupsWithOnDemandMembers(int quantityOnDemandMembers);
    List<Group> getGroupsWithCurrentMusicStyle(int styleId);
}
