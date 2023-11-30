package com.novi.app.service;

import com.novi.app.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MusicInstrumentService {
    // single musicInstrument operation
    // get info
    Optional<MusicInstrument> findMusicInstrumentById(Integer musicInstrumentId);
    Set<User> getMusicInstrumentUsers(Integer musicInstrumentId);
    Set<Group> getMusicInstrumentGroups(Integer musicInstrumentId);
    Set<MusicStyle> getMusicInstrumentMusicStyles(Integer musicInstrumentId);
    Set<Location> getMusicInstrumentLocations(Integer musicInstrumentId);

    // update info
    void saveMusicInstrument(MusicInstrument musicInstrument);
    void deleteMusicInstrument(Integer musicInstrumentId);
    void updateMusicInstrument(Integer musicInstrumentId, MusicInstrument musicInstrument);

    // many musicInstruments operation
    List<MusicInstrument> findAllMusicInstruments();
}
