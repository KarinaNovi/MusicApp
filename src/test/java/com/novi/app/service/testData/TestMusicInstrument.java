package com.novi.app.service.testData;

import com.novi.app.model.MusicInstrument;

public final class TestMusicInstrument {

    public static MusicInstrument createSimpleMusicInstrument() {
        String instrumentName = "Ukulele";
        return new MusicInstrument(instrumentName);
    }

    public static MusicInstrument updateSimpleMusicInstrument() {
        MusicInstrument musicInstrument = createSimpleMusicInstrument();
        musicInstrument.setInstrumentName("Drums");
        return musicInstrument;
    }
}
