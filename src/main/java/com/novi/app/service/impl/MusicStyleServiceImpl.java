package com.novi.app.service.impl;

import com.novi.app.model.*;
import com.novi.app.model.repository.MusicStyleRepository;
import com.novi.app.service.MusicStyleService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MusicStyleServiceImpl implements MusicStyleService {

    private final MusicStyleRepository musicStyleRepository;

    public MusicStyleServiceImpl(MusicStyleRepository musicStyleRepository) {
        this.musicStyleRepository = musicStyleRepository;
    }

    @Override
    public List<MusicStyle> findAllMusicStyles() {

        return musicStyleRepository.findAll();
    }

    @Override
    public void saveMusicStyle(MusicStyle musicStyle) {
        musicStyleRepository.save(musicStyle);
    }

    @Override
    public Optional<MusicStyle> findMusicStyleById(Integer id) {
        return musicStyleRepository.findById(id);
    }

    @Override
    public Set<User> getMusicStyleUsers(Integer musicStyleId) {
        Optional<MusicStyle> optionalMusicStyle = findMusicStyleById(musicStyleId);
        Set<User> musicStyleUsers = new HashSet<>();
        if (optionalMusicStyle.isPresent()) {
            MusicStyle musicStyle = optionalMusicStyle.get();
            musicStyleUsers.addAll(musicStyle.getUsers());
        } else {
            System.out.println("WARN: No existing musicStyle with such id");
        }
        return musicStyleUsers;
    }

    @Override
    public Set<Group> getMusicStyleGroups(Integer musicStyleId) {
        Optional<MusicStyle> optionalMusicStyle = findMusicStyleById(musicStyleId);
        Set<Group> groups = new HashSet<>();
        if (optionalMusicStyle.isPresent()) {
            MusicStyle musicStyle = optionalMusicStyle.get();
            groups.addAll(musicStyle.getGroups());
        } else {
            System.out.println("WARN: No existing musicStyle with such id");
        }
        return groups;
    }

    @Override
    public Set<MusicInstrument> getMusicStyleMusicInstruments(Integer musicStyleId) {
        Optional<MusicStyle> optionalMusicStyle = findMusicStyleById(musicStyleId);
        Set<MusicInstrument> musicInstruments = new HashSet<>();
        if (optionalMusicStyle.isPresent()) {
            MusicStyle musicStyle = optionalMusicStyle.get();
            musicInstruments.addAll(musicStyle.getMusicInstruments());
        } else {
            System.out.println("WARN: No existing musicStyle with such id");
        }
        return musicInstruments;
    }

    @Override
    public Set<Location> getMusicStyleLocations(Integer musicStyleId) {
        Optional<MusicStyle> optionalMusicStyle = findMusicStyleById(musicStyleId);
        Set<Location> locations = new HashSet<>();
        if (optionalMusicStyle.isPresent()) {
            MusicStyle musicStyle = optionalMusicStyle.get();
            locations.addAll(musicStyle.getLocations());
        } else {
            System.out.println("WARN: No existing musicStyle with such id");
        }
        return locations;
    }

    @Override
    public void deleteMusicStyle(Integer id) {
        musicStyleRepository.deleteById(id);
    }

    @Override
    public void updateMusicStyle(Integer musicStyleId, MusicStyle musicStyle) {
        Optional<MusicStyle> optionalMusicStyle = musicStyleRepository.findById(musicStyleId);
        if (optionalMusicStyle.isPresent()) {
            musicStyle.setStyleId(musicStyleId);
            musicStyleRepository.save(musicStyle);
        } else {
            System.out.println("WARN: No existing musicStyle with such id");
        }
    }
}