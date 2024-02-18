package dev.plasticstraw.inf_music.config.widget;

import java.util.function.Consumer;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.option.SimpleOption.TooltipFactory;
import net.minecraft.text.Text;

public class BooleanButton implements SimpleOptionWidget<Boolean> {

    private final SimpleOption<Boolean> simpleOption;

    public BooleanButton(
            String translationKey,
            @Nullable String tooltipTranslationKey,
            Boolean initialValue,
            Function<Boolean, Text> textCallback,
            Consumer<Boolean> callback) {
        this.simpleOption = SimpleOption.ofBoolean(
                translationKey,
                getTooltip(tooltipTranslationKey),
                (optionText, value) -> textCallback.apply(value),
                initialValue,
                (value) -> callback.accept(value));
    }

    public SimpleOption<Boolean> getSimpleOption() {
        return this.simpleOption;
    }

    private static TooltipFactory<Boolean> getTooltip(@Nullable String tooltipTranslationKey) {
        if (tooltipTranslationKey == null) {
            return SimpleOption.emptyTooltip();
        }

        return SimpleOption.constantTooltip(Text.translatable(tooltipTranslationKey));
    }

}
