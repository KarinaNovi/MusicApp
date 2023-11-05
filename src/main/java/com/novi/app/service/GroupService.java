package com.novi.app.service;

import com.novi.app.model.Group;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    // all groups with current style/
    //1 group operation
    void saveGroup(Group group);
    Optional<Group> findGroupById(Long id);
    void deleteGroup(Long id);
    void updateGroup(Long groupId, Group group);

    List<Group> getGroupsWithMusicStyle(Integer styleId);

    // many groups operation
    List<Group> findAllGroups();

}
