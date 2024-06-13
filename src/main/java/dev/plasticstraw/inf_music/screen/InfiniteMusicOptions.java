package dev.plasticstraw.inf_music.screen;

import dev.plasticstraw.inf_music.InfiniteMusic;
import dev.plasticstraw.inf_music.config.InfiniteMusicConfig.MusicOptions;
import dev.plasticstraw.inf_music.config.widget.BooleanButton;
import dev.plasticstraw.inf_music.config.widget.ClickableButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class InfiniteMusicOptions extends AbstractOptionsScreen {

    public InfiniteMusicOptions(Screen parent) {
        super(parent, "inf_music.config.title");
    }

    @Override
    public SimpleOption<?>[] getWidgets() {
        ClickableButton mainMenuMusicbutton = createMusicButton(
                "inf_music.config.delay.menu",
                InfiniteMusic.CONFIG.mainMenuMusic);

        ClickableButton gameplayMusicbutton = createMusicButton(
                "inf_music.config.delay.gameplay",
                InfiniteMusic.CONFIG.gameplayMusic);

        ClickableButton creativeMusicbutton = createMusicButton(
                "inf_music.config.delay.creative",
                InfiniteMusic.CONFIG.creativeMusic);

        ClickableButton underwaterMusicbutton = createMusicButton(
                "inf_music.config.delay.underwater",
                InfiniteMusic.CONFIG.underwaterMusic);

        ClickableButton endMusicbutton = createMusicButton(
                "inf_music.config.delay.end",
                InfiniteMusic.CONFIG.endMusic);

        BooleanButton pauseForDiscMusicButton = new BooleanButton(
                "inf_music.config.pauseMusic",
                "inf_music.config.pauseMusic.tooltip",
                InfiniteMusic.CONFIG.pauseForDiscMusic, (value) -> {
                    return value ? Text.translatable("inf_music.config.pauseMusic.enabled")
                            : Text.translatable("inf_music.config.pauseMusic.disabled");
                }, (value) -> {
                    InfiniteMusic.CONFIG.pauseForDiscMusic = value;
                });

        BooleanButton playMusicImmediatelyButton = new BooleanButton(
                "inf_music.config.playImmediately",
                "inf_music.config.playImmediately.tooltip",
                InfiniteMusic.CONFIG.playMusicImmediately, (value) -> {
                    return value ? Text
                            .translatable("inf_music.config.playImmediately.enabled")
                            : Text.translatable(
                                    "inf_music.config.playImmediately.disabled");
                }, (value) -> {
                    InfiniteMusic.CONFIG.playMusicImmediately = value;
                });

        return new SimpleOption[] { mainMenuMusicbutton.getSimpleOption(),
                gameplayMusicbutton.getSimpleOption(), creativeMusicbutton.getSimpleOption(),
                underwaterMusicbutton.getSimpleOption(), endMusicbutton.getSimpleOption(),
                pauseForDiscMusicButton.getSimpleOption(),
                playMusicImmediatelyButton.getSimpleOption() };
    }

    private ClickableButton createMusicButton(String translationKey, MusicOptions musicOptions) {
        return new ClickableButton(translationKey, () -> client
                .setScreen(new MusicDelayOptions(this, translationKey, musicOptions)));
    }

}
