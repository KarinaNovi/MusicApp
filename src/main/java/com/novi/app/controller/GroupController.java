package com.novi.app.controller;

import com.novi.app.model.Group;
import com.novi.app.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String getUser(Model model) {
        model.addAttribute("groups", groupService.findAllGroups());
        return "groups";
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public String getUser(@ModelAttribute("group") Group group) {
        String groupName = group.getGroupName();
        int maxQuantity = group.getMaxQuantity();
        int currentQuantity = group.getCurrentQuantity();
        String whoInDesc = group.getWhoInDesc();
        String whoNeedsDesc = group.getWhoNeedsDesc();
        String registrationDtm = String.valueOf(System.currentTimeMillis());
        // TODO: add automatic service to add max default value
        String deletionDtm = String.valueOf(1L);
        // TODO: how to get userId to set leaderId?
        String repetitionDate = group.getRepetitionDate();
        group = new Group(groupName, maxQuantity, currentQuantity, whoInDesc, whoNeedsDesc, registrationDtm, deletionDtm, repetitionDate);
        groupService.saveGroup(group);
        return "redirect:/groups";
    }
}



