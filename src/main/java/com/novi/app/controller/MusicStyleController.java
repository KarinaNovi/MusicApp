package com.novi.app.controller;

import com.novi.app.model.Group;
import com.novi.app.model.MusicInstrument;
import com.novi.app.model.MusicStyle;
import com.novi.app.model.User;
import com.novi.app.service.MusicStyleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/musicStyles")
public class MusicStyleController {

    private final MusicStyleService musicStyleService;

    public MusicStyleController(MusicStyleService musicStyleService) {
        this.musicStyleService = musicStyleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MusicStyle>> index(){
        List<MusicStyle> musicStyles = musicStyleService.findAllMusicStyles();
        return new ResponseEntity<>(musicStyles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MusicStyle>> getMusicStyleById(@PathVariable("id") Integer musicStyleId){
        return new ResponseEntity<>(musicStyleService.findMusicStyleById(musicStyleId), HttpStatus.OK);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Set<User>> getMusicStyleUsers(@PathVariable("id") Integer musicStyleId){
        return new ResponseEntity<>(musicStyleService.getMusicStyleUsers(musicStyleId), HttpStatus.OK);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<Set<Group>> getMusicStyleGroups(@PathVariable("id") Integer musicStyleId){
        return new ResponseEntity<>(musicStyleService.getMusicStyleGroups(musicStyleId), HttpStatus.OK);
    }

    @GetMapping("/{id}/instruments")
    public ResponseEntity<Set<MusicInstrument>> getMusicStyleMusicInstruments(@PathVariable("id") Integer musicStyleId){
        return new ResponseEntity<>(musicStyleService.getMusicStyleMusicInstruments(musicStyleId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Optional<MusicStyle>> create(@Valid @RequestBody MusicStyle musicStyle,
                                                 BindingResult bindingResult) {
        String musicStyleName = musicStyle.getStyleName();
        if (bindingResult.hasErrors()) {
            System.out.println("Mandatory parameter is null, check request body");
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        musicStyle = new MusicStyle(musicStyleName);
        musicStyleService.saveMusicStyle(musicStyle);
        return new ResponseEntity<>(musicStyleService.findMusicStyleById(musicStyle.getStyleId()), HttpStatus.CREATED);
    }

    @PostMapping("/updateMusicStyle/{id}")
    public ResponseEntity<Optional<MusicStyle>> update(@RequestBody MusicStyle musicStyle,
                                                 @PathVariable("id") Integer musicStyleId) {
        //TODO: validate - which parameters need to be updated, which need to be taken as is
        musicStyleService.updateMusicStyle(musicStyleId, musicStyle);
        return new ResponseEntity<>(musicStyleService.findMusicStyleById(musicStyle.getStyleId()), HttpStatus.OK);
    }

    // Not recommended physical deletion - manual corrections only!
    @DeleteMapping(value = "/deleteMusicStyle/{id}")
    public ResponseEntity<List<MusicStyle>> deleteMusicStyle(@PathVariable("id") Integer musicStyleId) {
        musicStyleService.deleteMusicStyle(musicStyleId);
        List<MusicStyle> musicStyles = musicStyleService.findAllMusicStyles();
        return new ResponseEntity<>(musicStyles, HttpStatus.OK);
    }
}



