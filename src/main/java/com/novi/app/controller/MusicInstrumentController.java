package com.novi.app.controller;

import com.novi.app.model.*;
import com.novi.app.model.MusicInstrument;
import com.novi.app.service.MusicInstrumentService;
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
@RequestMapping("/musicInstruments")
@CrossOrigin
public class MusicInstrumentController {

    private final MusicInstrumentService musicInstrumentService;

    public MusicInstrumentController(MusicInstrumentService musicInstrumentService) {
        this.musicInstrumentService = musicInstrumentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MusicInstrument>> index(){
        List<MusicInstrument> musicInstruments = musicInstrumentService.findAllMusicInstruments();
        return new ResponseEntity<>(musicInstruments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MusicInstrument>> getMusicInstrumentById(@PathVariable("id") Integer musicInstrumentId){
        return new ResponseEntity<>(musicInstrumentService.findMusicInstrumentById(musicInstrumentId), HttpStatus.OK);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Set<User>> getMusicInstrumentUsers(@PathVariable("id") Integer musicInstrumentId){
        return new ResponseEntity<>(musicInstrumentService.getMusicInstrumentUsers(musicInstrumentId), HttpStatus.OK);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<Set<Group>> getMusicInstrumentGroups(@PathVariable("id") Integer musicInstrumentId){
        return new ResponseEntity<>(musicInstrumentService.getMusicInstrumentGroups(musicInstrumentId), HttpStatus.OK);
    }

    @GetMapping("/{id}/styles")
    public ResponseEntity<Set<MusicStyle>> getMusicInstrumentMusicStyles(@PathVariable("id") Integer musicInstrumentId){
        return new ResponseEntity<>(musicInstrumentService.getMusicInstrumentMusicStyles(musicInstrumentId), HttpStatus.OK);
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<Set<Location>> getMusicInstrumentLocations(@PathVariable("id") Integer musicInstrumentId){
        return new ResponseEntity<>(musicInstrumentService.getMusicInstrumentLocations(musicInstrumentId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Optional<MusicInstrument>> create(@Valid @RequestBody MusicInstrument musicInstrument,
                                                 BindingResult bindingResult) {
        String musicInstrumentName = musicInstrument.getInstrumentName();
        if (bindingResult.hasErrors()) {
            System.out.println("Mandatory parameter is null, check request body");
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        musicInstrument = new MusicInstrument(musicInstrumentName);
        musicInstrumentService.saveMusicInstrument(musicInstrument);
        return new ResponseEntity<>(musicInstrumentService.findMusicInstrumentById(musicInstrument.getInstrumentId()), HttpStatus.CREATED);
    }

    @PostMapping("/updateMusicInstrument/{id}")
    public ResponseEntity<Optional<MusicInstrument>> update(@RequestBody MusicInstrument musicInstrument,
                                                 @PathVariable("id") Integer musicInstrumentId) {
        //TODO: validate - which parameters need to be updated, which need to be taken as is
        musicInstrumentService.updateMusicInstrument(musicInstrumentId, musicInstrument);
        return new ResponseEntity<>(musicInstrumentService.findMusicInstrumentById(musicInstrument.getInstrumentId()), HttpStatus.OK);
    }

    // Not recommended physical deletion - manual corrections only!
    @DeleteMapping(value = "/deleteMusicInstrument/{id}")
    public ResponseEntity<List<MusicInstrument>> deleteMusicInstrument(@PathVariable("id") Integer musicInstrumentId) {
        musicInstrumentService.deleteMusicInstrument(musicInstrumentId);
        List<MusicInstrument> musicInstruments = musicInstrumentService.findAllMusicInstruments();
        return new ResponseEntity<>(musicInstruments, HttpStatus.OK);
    }
}



