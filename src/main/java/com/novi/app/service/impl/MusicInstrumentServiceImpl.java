package com.novi.app.service.impl;

import com.novi.app.model.*;
import com.novi.app.model.repository.MusicInstrumentRepository;
import com.novi.app.service.MusicInstrumentService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MusicInstrumentServiceImpl implements MusicInstrumentService {

    private final MusicInstrumentRepository musicInstrumentRepository;

    public MusicInstrumentServiceImpl(MusicInstrumentRepository musicInstrumentRepository) {
        this.musicInstrumentRepository = musicInstrumentRepository;
    }

    @Override
    public List<MusicInstrument> findAllMusicInstruments() {

        return musicInstrumentRepository.findAll();
    }

    @Override
    public void saveMusicInstrument(MusicInstrument musicInstrument) {
        musicInstrumentRepository.save(musicInstrument);
    }

    @Override
    public Optional<MusicInstrument> findMusicInstrumentById(Integer id) {
        return musicInstrumentRepository.findById(id);
    }

    @Override
    public Set<User> getMusicInstrumentUsers(Integer musicInstrumentId) {
        Optional<MusicInstrument> optionalMusicInstrument = findMusicInstrumentById(musicInstrumentId);
        Set<User> musicInstrumentUsers = new HashSet<>();
        if (optionalMusicInstrument.isPresent()) {
            MusicInstrument musicInstrument = optionalMusicInstrument.get();
            musicInstrumentUsers.addAll(musicInstrument.getUsers());
        } else {
            System.out.println("WARN: No existing musicInstrument with such id");
        }
        return musicInstrumentUsers;
    }

    @Override
    public Set<Group> getMusicInstrumentGroups(Integer musicInstrumentId) {
        Optional<MusicInstrument> optionalMusicInstrument = findMusicInstrumentById(musicInstrumentId);
        Set<Group> groups = new HashSet<>();
        if (optionalMusicInstrument.isPresent()) {
            MusicInstrument musicInstrument = optionalMusicInstrument.get();
            groups.addAll(musicInstrument.getGroups());
        } else {
            System.out.println("WARN: No existing musicInstrument with such id");
        }
        return groups;
    }

    @Override
    public Set<MusicStyle> getMusicInstrumentMusicStyles(Integer musicInstrumentId) {
        Optional<MusicInstrument> optionalMusicInstrument = findMusicInstrumentById(musicInstrumentId);
        Set<MusicStyle> musicStyles = new HashSet<>();
        if (optionalMusicInstrument.isPresent()) {
            MusicInstrument musicInstrument = optionalMusicInstrument.get();
            musicStyles.addAll(musicInstrument.getMusicStyles());
        } else {
            System.out.println("WARN: No existing musicInstrument with such id");
        }
        return musicStyles;
    }

    @Override
    public Set<Location> getMusicInstrumentLocations(Integer musicInstrumentId) {
        Optional<MusicInstrument> optionalMusicInstrument = findMusicInstrumentById(musicInstrumentId);
        Set<Location> locations = new HashSet<>();
        if (optionalMusicInstrument.isPresent()) {
            MusicInstrument musicInstrument = optionalMusicInstrument.get();
            locations.addAll(musicInstrument.getLocations());
        } else {
            System.out.println("WARN: No existing musicInstrument with such id");
        }
        return locations;
    }

    @Override
    public void deleteMusicInstrument(Integer id) {
        musicInstrumentRepository.deleteById(id);
    }

    @Override
    public void updateMusicInstrument(Integer musicInstrumentId, MusicInstrument musicInstrument) {
        Optional<MusicInstrument> optionalMusicInstrument = musicInstrumentRepository.findById(musicInstrumentId);
        if (optionalMusicInstrument.isPresent()) {
            musicInstrument.setInstrumentId(musicInstrumentId);
            musicInstrumentRepository.save(musicInstrument);
        } else {
            System.out.println("WARN: No existing musicInstrument with such id");
        }
    }
}