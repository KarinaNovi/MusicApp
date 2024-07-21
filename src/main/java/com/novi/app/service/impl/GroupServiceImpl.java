package com.novi.app.service.impl;

import com.novi.app.model.*;
import com.novi.app.model.repository.GroupRepository;
import com.novi.app.model.repository.UserRepository;
import com.novi.app.service.GroupService;
import com.novi.app.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(
            GroupServiceImpl.class
    );

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
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
    public void terminateGroup(Long groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group groupToUpdate = optionalGroup.get();
            groupToUpdate.setDeletionDate(new Date());
            groupRepository.save(groupToUpdate);
        } else {
            System.out.println("WARN: No existing group with such id");
        }
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

    @Override
    public Set<Location> getGroupLocations(Long groupId) {
        Optional<Group> optionalGroup = findGroupById(groupId);
        Set<Location> locations = new HashSet<>();
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            locations.addAll(group.getLocations());
        } else {
            System.out.println("WARN: No existing group with such id");
        }
        return locations;
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
            group.setGroupId(groupId);
            groupRepository.save(group);
        } else {
            System.out.println("WARN: No existing group with such id");
        }
    }

    @Override
    public void addUser(Long groupId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("There is no user with such id, data is broken"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("There is no group with such id, data is broken"));
        group.getUsers().add(user);
        logger.info("users in current group: {}", group.getUsers().stream().map(User::getUserLogin).collect(Collectors.toList()));
        inheritMusicInstrumentFromUser(group, user);
        userRepository.save(user);
    }

    private void inheritMusicInstrumentFromUser(Group group,
                                                User user) {
        Set<MusicInstrument> musicInstruments = user.getMusicInstruments();
        if (!musicInstruments.isEmpty()) {
            group.getMusicInstruments().addAll(musicInstruments);
            logger.debug("Added music instruments from user {} to group {}: {}", user.getUserId(),
                    group.getGroupId(), musicInstruments);
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

    @Override
    public List<Group> findActiveGroups() {
        return groupRepository.findActiveGroups(new Date(Constants.MAX_DATE));
    }
}