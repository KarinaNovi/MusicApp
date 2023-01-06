package com.novi.app.model.DAO;

import com.novi.app.model.Group;

import java.util.List;

public interface GroupDAO {

    Group findById(int groupId);

    void save(Group group);

    void update(int groupId, Group updateGroup);

    void deleteById(int groupId);

    List<Group> findAll();

}
