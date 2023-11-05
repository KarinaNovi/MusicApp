package com.novi.app.service.impl;

import com.novi.app.model.Group;
import com.novi.app.model.MusicStyle;
import com.novi.app.model.repository.GroupRepository;
import com.novi.app.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findAllGroups() {

        return groupRepository.findAll();
    }

    @Override
    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    @Override
    public Optional<Group> findGroupById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public void updateGroup(Long groupId, Group group) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            groupRepository.save(group);
        } else {
            System.out.println("WARN: No existing user with such id");
        }
    }

    @Override
    public List<Group> getGroupsWithMusicStyle(Integer styleId) {
        MusicStyle musicStyle = new MusicStyle();
        musicStyle.setStyleId(1);
        musicStyle.setStyleName("Rock");
        return groupRepository.getGroupsByMusicStyles(musicStyle);
    }

}
