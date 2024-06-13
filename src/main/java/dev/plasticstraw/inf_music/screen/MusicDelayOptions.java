package dev.plasticstraw.inf_music.screen;

import dev.plasticstraw.inf_music.config.InfiniteMusicConfig.MusicOptions;
import dev.plasticstraw.inf_music.config.widget.BooleanButton;
import dev.plasticstraw.inf_music.config.widget.IntegerSlider;
import dev.plasticstraw.inf_music.config.widget.PresetButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class MusicDelayOptions extends AbstractOptionsScreen {

    private final MusicOptions musicOptions;
    private final int maxIntValue;
    private final int secondsStep;
    private final String valueTranslationKey;

    private PresetButton presetButton;
    private BooleanButton disableMusicButton;
    private IntegerSlider delaySlider;
    private IntegerSlider randomnessSlider;
    private MultilineTextWidget contextTextWidget;

    public MusicDelayOptions(Screen parent, String titleTranslationKey, MusicOptions musicOptions) {
        super(parent, titleTranslationKey);
        this.musicOptions = musicOptions;

        if (musicOptions.usesMinutes()) {
            maxIntValue = 15;
            secondsStep = 60;
            valueTranslationKey = "minute";
        } else {
            maxIntValue = 120;
            secondsStep = 1;
            valueTranslationKey = "second";
        }
    }

    @Override
    protected SimpleOption<?>[] getWidgets() {
        presetButton = new PresetButton(
                "inf_music.config.delayPreset",
                getPresetTranslationKey(musicOptions.delay, musicOptions.randomness),
                () -> {
                    if (musicOptions.delay == 0 && musicOptions.randomness == 0) {
                        delaySlider.getSimpleOption()
                                .setValue(musicOptions.getDefaultDelay() / secondsStep);
                        randomnessSlider.getSimpleOption().setValue(
                                musicOptions.getDefaultRandomness() / secondsStep);
                    } else {
                        delaySlider.getSimpleOption().setValue(0);
                        randomnessSlider.getSimpleOption().setValue(0);
                    }
                    clearAndInit();
                });

        disableMusicButton = new BooleanButton(
                "inf_music.config.disableMusic", null,
                musicOptions.enabled,
                (value) -> {
                    return value ? Text.translatable("inf_music.config.disableMusic.enabled")
                            : Text.translatable("inf_music.config.disableMusic.disabled");
                },
                (value) -> {
                    musicOptions.enabled = value;
                });

        delaySlider = new IntegerSlider(
                "inf_music.config.delay", 0, maxIntValue,
                musicOptions.delay / secondsStep, (value) -> {
                    return getValueText(value * secondsStep);
                },
                (value) -> {
                    int seconds = value * secondsStep;
                    presetButton.setValueTranslationKey(
                            getPresetTranslationKey(seconds, musicOptions.randomness));
                    musicOptions.delay = seconds;
                    updateContextTextWidget();
                });

        randomnessSlider = new IntegerSlider(
                "inf_music.config.randomness",
                0,
                maxIntValue,
                musicOptions.randomness / secondsStep,
                (value) -> {
                    if (value == 0) {
                        return Text.translatable("inf_music.config.randomness.none");
                    }
                    return getValueText(value * secondsStep);
                },
                (value) -> {
                    int seconds = value * secondsStep;
                    presetButton.setValueTranslationKey(
                            getPresetTranslationKey(musicOptions.delay, seconds));
                    musicOptions.randomness = seconds;
                    updateContextTextWidget();
                });

        return new SimpleOption<?>[] {
                presetButton.getSimpleOption(),
                disableMusicButton.getSimpleOption(),
                delaySlider.getSimpleOption(),
                randomnessSlider.getSimpleOption() };

    }

    @Override
    protected void initBody() {
        super.initBody();
        contextTextWidget = addDrawableChild(new MultilineTextWidget(Text.empty(), textRenderer));
        contextTextWidget.setMaxWidth(body.getRowWidth() - 50);
        contextTextWidget.setCentered(true);
        contextTextWidget.setY(40 + 2 * 24);
        updateContextTextWidget();
    }

    private String getPresetTranslationKey(int delay, int randomness) {
        if (delay == 0 && randomness == 0) {
            return "inf_music.config.delayPreset.none";
        }

        if (delay == musicOptions.getDefaultDelay() && randomness == musicOptions.getDefaultRandomness()) {
            return "inf_music.config.delayPreset.default";
        }

        return "inf_music.config.delayPreset.custom";
    }

    private Text getValueText(int value) {
        value /= secondsStep;
        return Text.translatable("inf_music." + valueTranslationKey + (value == 1 ? "" : "s"),
                value);
    }

    private void updateContextTextWidget() {
        Text message;
        if (musicOptions.randomness == 0) {
            message = Text.translatable("inf_music.config.delayContext", getValueText(musicOptions.delay));
        } else {
            message = Text.translatable(
                    "inf_music.config.delayContext.random",
                    getValueText(Math.max(musicOptions.delay - musicOptions.randomness, 0)),
                    getValueText(musicOptions.delay + musicOptions.randomness));
        }

        contextTextWidget.setMessage(message);
        contextTextWidget.setX((width - contextTextWidget.getWidth()) / 2);
    }

}
