package com.novi.app.service.impl;

import com.novi.app.model.Group;
import com.novi.app.model.repository.GroupRepository;
import com.novi.app.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;
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