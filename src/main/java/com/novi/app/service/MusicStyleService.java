package com.novi.app.service;

import com.novi.app.model.*;
import com.novi.app.model.MusicStyle;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MusicStyleService {
    // single musicStyle operation
    // get info
    Optional<MusicStyle> findMusicStyleById(Integer musicStyleId);
    Set<User> getMusicStyleUsers(Integer musicStyleId);
    Set<Group> getMusicStyleGroups(Integer musicStyleId);
    Set<MusicInstrument> getMusicStyleMusicInstruments(Integer musicStyleId);
    Set<Location> getMusicStyleLocations(Integer musicStyleId);

    // update info
    void saveMusicStyle(MusicStyle musicStyle);
    void deleteMusicStyle(Integer musicStyleId);
    void updateMusicStyle(Integer musicStyleId, MusicStyle musicStyle);

    // many musicStyles operation
    List<MusicStyle> findAllMusicStyles();
}
