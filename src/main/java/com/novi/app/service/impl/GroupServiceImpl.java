package com.novi.app.service.impl;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
import com.novi.app.model.User;
import com.novi.app.model.repository.GroupRepository;
import com.novi.app.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.constant.Constable;
import java.util.*;

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
    public Set<User> getGroupMembers(Long groupId) {
        Optional<Group> optionalGroup = findGroupById(groupId);
        Set<User> groupMembers = new HashSet<>();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            groupMembers.addAll(group.getUsers());
        } else {
            System.out.println("WARN: No existing group with such id");
        }
        return groupMembers;
    }

    @Override
    public Set<MusicStyle> getGroupMusicStyles(Long groupId) {
        Optional<Group> optionalGroup = findGroupById(groupId);
        Set<MusicStyle> musicStyles = new HashSet<>();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            musicStyles.addAll(group.getMusicStyles());
        } else {
            System.out.println("WARN: No existing group with such id");
        }
        return musicStyles;
    }

    @Override
    public Set<MusicInstrument> getGroupMusicInstruments(Long groupId) {
        Optional<Group> optionalGroup = findGroupById(groupId);
        Set<MusicInstrument> musicInstruments = new HashSet<>();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            musicInstruments.addAll(group.getMusicInstruments());
        } else {
            System.out.println("WARN: No existing group with such id");
        }
        return musicInstruments;
    }

    // TODO: maybe return user from userService
    @Override
    public Long getGroupLeaderId(Long groupId) {
        Optional<Group> optionalGroup = findGroupById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            return group.getLeaderId();
        } else {
            System.out.println("WARN: No existing user with such id");
            return null;
        }
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
            System.out.println("WARN: No existing group with such id");
        }
    }

    @Override
    public List<Group> getGroupsWithOnDemandMembers(int quantityOnDemandMembers) {
        return groupRepository
                .findAll()
                .stream()
                .filter(group -> (group.getMaxQuantity() - group.getCurrentQuantity()) == quantityOnDemandMembers)
                .toList();
    }

    @Override
    public List<Group> getGroupsWithCurrentMusicStyle(int styleId) {
        return groupRepository
                .findAll()
                .stream()
                .filter(group -> !(group.getMusicStyles()
                        .stream()
                        .filter(musicStyle -> musicStyle.getStyleId() == styleId)
                        .toList().isEmpty()))
                .toList();
    }

    @Override
    public List<Group> findNewlyCreatedGroups() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.MONTH, -1);
        return groupRepository.findNewlyCreatedGroups(currentDate.getTime());
    }
}