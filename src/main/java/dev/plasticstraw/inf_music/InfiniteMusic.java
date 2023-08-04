package dev.plasticstraw.inf_music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig.MusicOptions;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;

public class InfiniteMusic implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("inf-music");
    public static final InfiniteMusicConfig CONFIG = InfiniteMusicConfig.load();

    @Override
    public void onInitializeClient() {
        updateMusicDelays();
    }

    public static void updateMusicDelays() {
        updateMusicDelay(MusicType.MENU, CONFIG.mainMenuMusic);
        updateMusicDelay(MusicType.GAME, CONFIG.gameplayMusic);
        updateMusicDelay(MusicType.CREATIVE, CONFIG.creativeMusic);
        updateMusicDelay(MusicType.UNDERWATER, CONFIG.underwaterMusic);
        updateMusicDelay(MusicType.END, CONFIG.endMusic);
    }

    private static void updateMusicDelay(MusicSound soundType, MusicOptions config) {
        soundType.updateMusicDelays(config.getMinTicks(), config.getMaxTicks(), config.enabled);
    }
}
