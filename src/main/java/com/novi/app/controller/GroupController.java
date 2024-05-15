package com.novi.app.controller;

import com.novi.app.model.*;
import com.novi.app.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/groups")
@CrossOrigin
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Group>> index(){
        List<Group> groups = groupService.findAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Group>> getGroupById(@PathVariable("id") Long groupId){
        return new ResponseEntity<>(groupService.findGroupById(groupId), HttpStatus.OK);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Set<User>> getGroupMembers(@PathVariable("id") Long groupId){
        return new ResponseEntity<>(groupService.getGroupMembers(groupId), HttpStatus.OK);
    }

    @GetMapping("/{id}/styles")
    public ResponseEntity<Set<MusicStyle>> getGroupMusicStyles(@PathVariable("id") Long groupId){
        return new ResponseEntity<>(groupService.getGroupMusicStyles(groupId), HttpStatus.OK);
    }

    @GetMapping("/{id}/instruments")
    public ResponseEntity<Set<MusicInstrument>> getGroupMusicInstruments(@PathVariable("id") Long groupId){
        return new ResponseEntity<>(groupService.getGroupMusicInstruments(groupId), HttpStatus.OK);
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<Set<Location>> getGroupLocations(@PathVariable("id") Long groupId){
        return new ResponseEntity<>(groupService.getGroupLocations(groupId), HttpStatus.OK);
    }

    @GetMapping("/{id}/leaderInfo")
    public ResponseEntity<Long> getLeaderGroups(@PathVariable("id") Long groupId){
        return new ResponseEntity<>(groupService.getGroupLeaderId(groupId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Optional<Group>> create(@Valid @RequestBody Group group,
                                                 BindingResult bindingResult) {
        String groupName = group.getGroupName();
        int maxQuantity = group.getMaxQuantity();
        int currentQuantity = group.getCurrentQuantity();
        String description = group.getDescription();
        Date repetitionDtm = group.getRepetitionDtm();
        String password = group.getPassword();
        long leaderId = group.getLeaderId();
        if (bindingResult.hasErrors()) {
            System.out.println("Mandatory parameter is null, check request body");
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        group = new Group(groupName, maxQuantity, currentQuantity, description, repetitionDtm, password, leaderId);
        groupService.saveGroup(group);
        return new ResponseEntity<>(groupService.findGroupById(group.getGroupId()), HttpStatus.CREATED);
    }

    @PostMapping("/updateGroup/{id}")
    public ResponseEntity<Optional<Group>> update(@RequestBody Group group,
                                                 @PathVariable("id") Long groupId) {
        //TODO: validate - which parameters need to be updated, which need to be taken as is
        groupService.updateGroup(groupId, group);
        return new ResponseEntity<>(groupService.findGroupById(group.getGroupId()), HttpStatus.OK);
    }

    // terminate = set deletionDate as sysdate only
    @PostMapping(value = "/terminateGroup/{id}")
    public ResponseEntity<Optional<Group>> terminateGroup(@PathVariable("id") Long groupId) {
        Optional<Group> user = groupService.findGroupById(groupId);
        user.ifPresent(value -> value.setDeletionDate(new Date()));
        groupService.terminateGroup(groupId);
        return new ResponseEntity<>(groupService.findGroupById(groupId), HttpStatus.OK);
    }

    // Not recommended physical deletion - manual corrections only!
    @DeleteMapping(value = "/deleteGroup/{id}")
    public ResponseEntity<List<Group>> deleteGroup(@PathVariable("id") Long groupId) {
        groupService.deleteGroup(groupId);
        List<Group> groups = groupService.findAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    /**
     * Many groups operations
     */
    @GetMapping("/activeGroups")
    public ResponseEntity<List<Group>> getActiveGroups() {
        return new ResponseEntity<>(groupService.findActiveGroups(), HttpStatus.OK);
    }

    @GetMapping("/newGroups")
    public ResponseEntity<List<Group>> getNewGroups() {
        return new ResponseEntity<>(groupService.findNewlyCreatedGroups(), HttpStatus.OK);
    }

    // TODO: move to musicController: /styles/{1}/groups
    @GetMapping("/groupsWithStyle/{id}")
    public ResponseEntity<List<Group>> getGroupsOfCurrentMusicStyle(@PathVariable("id") Integer musicStyleId) {
        return new ResponseEntity<>(groupService.getGroupsWithCurrentMusicStyle(musicStyleId), HttpStatus.OK);
    }
}



