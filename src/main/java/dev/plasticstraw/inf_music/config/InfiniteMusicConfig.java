package dev.plasticstraw.inf_music.config;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class InfiniteMusicConfig {

    private static final String CONFIG_FILE_NAME = "infinite-music-options.json";
    private static final Gson json = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting().excludeFieldsWithModifiers(Modifier.PRIVATE).create();

    public final MainMenuMusic mainMenuMusic = new MainMenuMusic();
    public final GameplayMusic gameplayMusic = new GameplayMusic();
    public final GameplayMusic creativeMusic = new GameplayMusic();
    public final GameplayMusic underwaterMusic = new GameplayMusic();
    public final EndMusic endMusic = new EndMusic();
    public boolean pauseForDiscMusic = true;
    public boolean playMusicImmediately = false;
    public boolean enableSoundOptionsButton = true;

    public static class MainMenuMusic extends MusicOptions {

        @Override
        public int getDefaultDelay() {
            return 15;
        }

        @Override
        public int getDefaultRandomness() {
            return 15;
        }

        @Override
        public boolean usesMinutes() {
            return false;
        }

    }

    public static class GameplayMusic extends MusicOptions {

        @Override
        public int getDefaultDelay() {
            return 900;
        }

        @Override
        public int getDefaultRandomness() {
            return 300;
        }

    }

    public static class EndMusic extends MusicOptions {

        @Override
        public int getDefaultDelay() {
            return 780;
        }

        @Override
        public int getDefaultRandomness() {
            return 420;
        }

    }

    public static abstract class MusicOptions implements MusicDefaults {

        public boolean enabled = true;
        public int delay = 0;
        public int randomness = 0;

        public int getMinTicks() {
            return Math.max(delay - randomness, 0) * 20;
        }

        public int getMaxTicks() {
            return (delay + randomness) * 20;
        }

    }

    private static interface MusicDefaults {

        public int getDefaultDelay();

        public int getDefaultRandomness();

        default public boolean usesMinutes() {
            return true;
        }
    }

    public static InfiniteMusicConfig load() {
        InfiniteMusicConfig config;
        Path path = getConfigPath();

        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile())) {
                config = json.fromJson(reader, InfiniteMusicConfig.class);
            } catch (IOException exception) {
                throw new RuntimeException("Invalid config", exception);
            }
        } else {
            config = new InfiniteMusicConfig();
        }

        return config;
    }

    public void write() {
        Path directory = getConfigPath().getParent();

        try {
            Files.createDirectories(directory);
        } catch (IOException exception) {
            throw new RuntimeException("Invalid directory", exception);
        }

        try {
            Files.writeString(getConfigPath(), json.toJson(this));
        } catch (IOException exception) {
            throw new RuntimeException("Couldn't write config", exception);
        }
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME);
    }

}
