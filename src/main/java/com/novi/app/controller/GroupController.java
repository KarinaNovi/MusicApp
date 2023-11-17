package com.novi.app.controller;

import com.novi.app.model.Group;
import com.novi.app.service.GroupService;
import com.novi.app.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupService.findAllGroups());
        return "groups";
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public String getGroup(@ModelAttribute("group") Group group) {
        String groupName = group.getGroupName();
        int maxQuantity = group.getMaxQuantity();
        int currentQuantity = group.getCurrentQuantity();
        String description = group.getDescription();
        Date registrationDate = new Date();
        Date deletionDate = new Date(Constants.MAX_DATE);
        Date repetitionDtm = group.getRepetitionDtm();
        group = new Group(groupName, maxQuantity, currentQuantity, description, registrationDate, deletionDate, repetitionDtm);
        groupService.saveGroup(group);
        return "redirect:/groups";
    }
}



