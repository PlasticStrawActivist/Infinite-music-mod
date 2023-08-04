package dev.plasticstraw.inf_music.util;

public interface MusicSoundInterface {

    default public void updateMusicDelays(int minDelay, int maxDelay, boolean enabled) {
        return;
    }

    default public boolean enabled() {
        return true;
    };

}
