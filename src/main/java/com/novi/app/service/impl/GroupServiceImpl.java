package com.novi.app.service.impl;

import com.novi.app.model.Group;
import com.novi.app.model.repository.GroupRepository;
import com.novi.app.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findAll() {

        return new ArrayList<>(groupRepository.findAll());
    }

    @Override
    public void save(Group group) {
        groupRepository.save(group);
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

}
