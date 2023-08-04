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
            this.maxIntValue = 15;
            this.secondsStep = 60;
            this.valueTranslationKey = "minute";
        } else {
            this.maxIntValue = 120;
            this.secondsStep = 1;
            this.valueTranslationKey = "second";
        }
    }

    @Override
    protected void initWidgets() {
        this.presetButton = new PresetButton(
                "inf_music.config.delayPreset",
                this.getPresetTranslationKey(this.musicOptions.delay, this.musicOptions.randomness),
                () -> {
                    if (this.musicOptions.delay == 0 && this.musicOptions.randomness == 0) {
                        this.delaySlider.getSimpleOption()
                                .setValue(this.musicOptions.getDefaultDelay() / this.secondsStep);
                        this.randomnessSlider.getSimpleOption().setValue(
                                this.musicOptions.getDefaultRandomness() / this.secondsStep);
                    } else {
                        this.delaySlider.getSimpleOption().setValue(0);
                        this.randomnessSlider.getSimpleOption().setValue(0);
                    }
                    this.clearAndInit();
                });

        this.disableMusicButton = new BooleanButton(
                "inf_music.config.disableMusic",
                this.musicOptions.enabled,
                (value) -> {
                    return value ? Text.translatable("inf_music.config.disableMusic.enabled")
                            : Text.translatable("inf_music.config.disableMusic.disabled");
                },
                (value) -> {
                    this.musicOptions.enabled = value;
                });

        this.delaySlider = new IntegerSlider(
                "inf_music.config.delay", 0, this.maxIntValue,
                this.musicOptions.delay / this.secondsStep, (value) -> {
                    return this.getValueText(value * this.secondsStep);
                },
                (value) -> {
                    int seconds = value * this.secondsStep;
                    this.presetButton.setValueTranslationKey(
                            this.getPresetTranslationKey(seconds, this.musicOptions.randomness));
                    this.musicOptions.delay = seconds;
                    this.updateContextTextWidget();
                });

        this.randomnessSlider = new IntegerSlider(
                "inf_music.config.randomness",
                0,
                this.maxIntValue,
                this.musicOptions.randomness / this.secondsStep,
                (value) -> {
                    if (value == 0) {
                        return Text.translatable("inf_music.config.randomness.none");
                    }
                    return this.getValueText(value * this.secondsStep);
                },
                (value) -> {
                    int seconds = value * this.secondsStep;
                    this.presetButton.setValueTranslationKey(
                            this.getPresetTranslationKey(this.musicOptions.delay, seconds));
                    this.musicOptions.randomness = seconds;
                    this.updateContextTextWidget();
                });

        this.optionButtons.addAll(new SimpleOption<?>[] {
                this.presetButton.getSimpleOption(),
                this.disableMusicButton.getSimpleOption(),
                this.delaySlider.getSimpleOption(),
                this.randomnessSlider.getSimpleOption() });

        this.contextTextWidget = this.addDrawableChild(new MultilineTextWidget(Text.empty(), this.textRenderer));
        this.contextTextWidget.setMaxWidth(this.optionButtons.getRowWidth() - 50);
        this.contextTextWidget.setCentered(true);
        this.contextTextWidget.setY(40 + 2 * 24);
        this.updateContextTextWidget();

    }

    private String getPresetTranslationKey(int delay, int randomness) {
        if (delay == 0 && randomness == 0) {
            return "inf_music.config.delayPreset.none";
        }

        if (delay == this.musicOptions.getDefaultDelay() && randomness == this.musicOptions.getDefaultRandomness()) {
            return "inf_music.config.delayPreset.default";
        }

        return "inf_music.config.delayPreset.custom";
    }

    private Text getValueText(int value) {
        value /= this.secondsStep;
        return Text.translatable("inf_music." + valueTranslationKey + (value == 1 ? "" : "s"),
                value);
    }

    private void updateContextTextWidget() {
        Text message;
        if (this.musicOptions.randomness == 0) {
            message = Text.translatable("inf_music.config.delayContext", this.getValueText(this.musicOptions.delay));
        } else {
            message = Text.translatable(
                    "inf_music.config.delayContext.random",
                    this.getValueText(Math.max(this.musicOptions.delay - this.musicOptions.randomness, 0)),
                    this.getValueText(this.musicOptions.delay + this.musicOptions.randomness));
        }

        this.contextTextWidget.setMessage(message);
        this.contextTextWidget.setX((this.width - contextTextWidget.getWidth()) / 2);
    }

}
