package dev.plasticstraw.inf_music.screen;

import dev.plasticstraw.inf_music.InfiniteMusic;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig.MusicOptions;
import dev.plasticstraw.inf_music.config.widget.ClickableButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.SimpleOption;

public class InfiniteMusicOptions extends AbstractOptionsScreen {

    private static final String TITLE_KEY = "inf_music.config.title";

    public InfiniteMusicOptions(Screen parent) {
        super(parent, TITLE_KEY);
    }

    @Override
    public void initWidgets() {
        ClickableButton mainMenuMusicbutton = this.createMusicButton(
                "inf_music.config.delay.menu",
                InfiniteMusic.CONFIG.mainMenuMusic);

        ClickableButton gameplayMusicbutton = this.createMusicButton(
                "inf_music.config.delay.gameplay",
                InfiniteMusic.CONFIG.gameplayMusic);

        ClickableButton creativeMusicbutton = this.createMusicButton(
                "inf_music.config.delay.creative",
                InfiniteMusic.CONFIG.creativeMusic);

        ClickableButton underwaterMusicbutton = this.createMusicButton(
                "inf_music.config.delay.underwater",
                InfiniteMusic.CONFIG.underwaterMusic);

        ClickableButton endMusicbutton = this.createMusicButton(
                "inf_music.config.delay.end",
                InfiniteMusic.CONFIG.endMusic);

        this.optionButtons.addAll(new SimpleOption[] { mainMenuMusicbutton.getSimpleOption(),
                gameplayMusicbutton.getSimpleOption(), creativeMusicbutton.getSimpleOption(),
                underwaterMusicbutton.getSimpleOption(), endMusicbutton.getSimpleOption() });
    }

    private ClickableButton createMusicButton(String translationKey, MusicOptions musicOptions) {
        return new ClickableButton(
                translationKey,
                () -> this.client.setScreen(new MusicDelayOptions(this, translationKey, musicOptions)));
    }

}
