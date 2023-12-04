package com.novi.app.service.testData;

import com.novi.app.model.MusicStyle;
import com.novi.app.model.User;

import java.time.LocalDate;

public final class TestMusicStyle {

    public static MusicStyle createSimpleMusicStyle() {
        String styleName = "New wave";
        return new MusicStyle(styleName);
    }

    public static MusicStyle updateSimpleMusicStyle() {
        MusicStyle musicStyle = createSimpleMusicStyle();
        musicStyle.setStyleName("Classic");
        return musicStyle;
    }
}
