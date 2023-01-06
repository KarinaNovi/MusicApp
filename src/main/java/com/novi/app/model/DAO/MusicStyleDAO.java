package com.novi.app.model.DAO;

import com.novi.app.model.MusicStyle;

import java.util.List;

public interface MusicStyleDAO {

    MusicStyle findById(int styleId);

    void save(MusicStyle musicStyle);

    void update(int styleId, MusicStyle updateMusicStyle);

    void deleteById(int styleId);

    List<MusicStyle> findAll();
}
