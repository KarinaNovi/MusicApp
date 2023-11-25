package com.novi.app.service;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
import com.novi.app.model.User;

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
    Long getGroupLeaderId(Long groupId);

    // update info
    void saveGroup(Group group);
    void deleteGroup(Long groupId);
    void updateGroup(Long groupId, Group group);

    // many groups operation
    List<Group> findAllGroups();
    List<Group> findNewlyCreatedGroups();
    List<Group> getGroupsWithOnDemandMembers(int quantityOnDemandMembers);
    List<Group> getGroupsWithCurrentMusicStyle(int styleId);
}
