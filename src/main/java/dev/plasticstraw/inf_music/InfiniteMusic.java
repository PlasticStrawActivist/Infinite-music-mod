package dev.plasticstraw.inf_music;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig.MusicOptions;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;

public class InfiniteMusic implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("inf-music");
    public static final InfiniteMusicConfig CONFIG = InfiniteMusicConfig.load();

    private static final List<MusicSound> gameplayMusic = new ArrayList<MusicSound>();
    public static SoundInstance musicInstance;

    @Override
    public void onInitializeClient() {
        updateMusicDelay(MusicType.MENU, CONFIG.mainMenuMusic);
    }

    public static void addGameplayMusic(MusicSound musicSound) {
        gameplayMusic.add(musicSound);
    }

    public static void updateMusicDelays() {
        updateMusicDelay(MusicType.MENU, CONFIG.mainMenuMusic);
        updateMusicDelay(MusicType.CREATIVE, CONFIG.creativeMusic);
        updateMusicDelay(MusicType.UNDERWATER, CONFIG.underwaterMusic);
        updateMusicDelay(MusicType.END, CONFIG.endMusic);

        for (MusicSound musicSound : gameplayMusic) {
            if (!(musicSound == MusicType.MENU || musicSound == MusicType.CREATIVE
                    || musicSound == MusicType.UNDERWATER || musicSound == MusicType.END)) {
                updateMusicDelay(musicSound, CONFIG.gameplayMusic);
            }
        }
    }

    private static void updateMusicDelay(MusicSound musicSound, MusicOptions config) {
        musicSound.updateMusicDelays(config.getMinTicks(), config.getMaxTicks(), config.enabled);
    }

}
