package dev.plasticstraw.inf_music;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig.MusicOptions;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class InfiniteMusic implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("inf-music");
    public static final InfiniteMusicConfig CONFIG = InfiniteMusicConfig.load();
    public static final Tracker TRACKER = new Tracker();

    private static final List<MusicSound> gameplayMusic = new ArrayList<MusicSound>();
    public static final List<SoundInstance> musicDiscInstanceList = new ArrayList<SoundInstance>();
    public static final MinecraftClient client = MinecraftClient.getInstance();
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

    public static boolean isMusicDiscMusicPlaying() {
        for (SoundInstance soundInstance : new ArrayList<SoundInstance>(musicDiscInstanceList)) {
            if (MinecraftClient.getInstance().getSoundManager().isPlaying(soundInstance)) {
                return true;
            }

            musicDiscInstanceList.remove(soundInstance);
        }

        if (musicDiscInstanceList.size() == 0) {
            return false;
        }

        return true;
    }

    private static void updateMusicDelay(MusicSound musicSound, MusicOptions config) {
        musicSound.updateMusicDelays(config.getMinTicks(), config.getMaxTicks(), config.enabled);
    }

    public static class Tracker {

        @Nullable
        private SoundInstance current;
        private final Random random = Random.create();
        private int timeUntilNextSong = Integer.MAX_VALUE;
        private boolean hasJoinedWorld = true;

        public void tick() {
            MusicSound musicSound = client.getMusicType();

            if (!musicSound.enabled()) {
                stop(musicSound);
                return;
            }

            if (hasJoinedWorld && !musicSound.equals(MusicType.MENU)) {
                hasJoinedWorld = false;

                if (CONFIG.playMusicImmediately) {
                    play(musicSound);
                    return;
                }
            }

            if (current != null) {
                if (!(client.getSoundManager().isPlaying(current) || isDiscMusicBlocking())) {
                    current = null;
                    timeUntilNextSong = MathHelper.nextInt(random, musicSound.getMinDelay(), musicSound.getMaxDelay());
                }

                if (!isPlayingType(musicSound) && musicSound.shouldReplaceCurrentMusic()) {
                    stop();
                    timeUntilNextSong = MathHelper.nextInt(random, 0, musicSound.getMinDelay() / 2);
                }
            }

            if (current == null) {
                timeUntilNextSong = Math.min(timeUntilNextSong, musicSound.getMaxDelay());
                if (timeUntilNextSong-- <= 0) {
                    play(musicSound);
                }
            }
        }

        public void play(MusicSound type) {
            if (type.getSound() == SoundManager.MISSING_SOUND) {
                this.timeUntilNextSong = Integer.MAX_VALUE;
                return;
            }

            if (isDiscMusicBlocking()) {
                return;
            }

            current = PositionedSoundInstance.music(type.getSound().value());
            InfiniteMusic.musicInstance = current;
            client.getSoundManager().play(current);
        }

        public void stop(MusicSound type) {
            if (this.isPlayingType(type)) {
                this.stop();
            }
        }

        public void stop() {
            if (current == null) {
                // add 100??
                return;
            }
            client.getSoundManager().stop(current);
        }

        public boolean isPlayingType(MusicSound musicSound) {
            if (current == null) {
                return false;
            }
            return musicSound.getSound().value().getId().equals(current.getId());
        }

        public void hasJoinedWorld() {
            hasJoinedWorld = true;
        }

        private boolean isDiscMusicBlocking() {
            return CONFIG.pauseForDiscMusic && isMusicDiscMusicPlaying();
        }

    }

}
