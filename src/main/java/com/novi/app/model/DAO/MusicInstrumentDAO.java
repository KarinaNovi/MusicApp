package com.novi.app.model.DAO;

import com.novi.app.model.MusicInstrument;

import java.util.List;

public interface MusicInstrumentDAO {

    MusicInstrument findById(int instrumentId);

    void save(MusicInstrument musicInstrument);

    void update(int instrumentId, MusicInstrument updateMusicInstrument);

    void deleteById(int instrumentId);

    List<MusicInstrument> findAll();
}
