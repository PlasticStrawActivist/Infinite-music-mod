package dev.plasticstraw.inf_music.config.widget;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.option.SimpleOption.Callbacks;
import net.minecraft.client.option.SimpleOption.TooltipFactory;
import net.minecraft.client.option.SimpleOption.ValueTextGetter;
import net.minecraft.text.Text;

public class PresetButton implements SimpleOptionWidget<Boolean> {

    private final SimpleOption<Boolean> simpleOption;
    private final Runnable onPress;
    private ButtonWidget widget;
    private String valueTranslationKey;

    public PresetButton(String translationKey, String valueTranslationKey, Runnable onPress) {
        this.simpleOption = new SimpleOption<Boolean>(
                translationKey,
                SimpleOption.emptyTooltip(),
                new TextGetter(),
                new WidgetCallback(), Codec.BOOL, true, (value) -> {
                });
        this.valueTranslationKey = valueTranslationKey;
        this.onPress = onPress;
    }

    @Override
    public SimpleOption<Boolean> getSimpleOption() {
        return this.simpleOption;
    }

    public void setValueTranslationKey(String valueTranslationKey) {
        if (this.widget != null) {
            this.valueTranslationKey = valueTranslationKey;
            this.widget.setMessage(this.simpleOption.textGetter.apply(true));
        }
    }

    private class TextGetter implements ValueTextGetter<Boolean> {

        @Override
        public Text toString(Text text, Boolean value) {
            return Text.translatable(
                    "inf_music.config.button",
                    text,
                    Text.translatable(PresetButton.this.valueTranslationKey));
        }
    }

    private class WidgetCallback implements Callbacks<Boolean> {

        @Override
        public Function<SimpleOption<Boolean>, ClickableWidget> getWidgetCreator(
                TooltipFactory<Boolean> var1,
                GameOptions options,
                int x,
                int y,
                int width,
                Consumer<Boolean> consumer) {
            return (simpleOption) -> {
                return PresetButton.this.widget = ButtonWidget.builder(
                        simpleOption.textGetter.apply(true),
                        new ButtonAction()).dimensions(x, y, width, 20).build();
            };
        }

        @Override
        public Optional<Boolean> validate(Boolean var1) {
            return Optional.empty();
        }

        @Override
        public Codec<Boolean> codec() {
            return Codec.BOOL;
        }

    }

    private class ButtonAction implements PressAction {

        @Override
        public void onPress(ButtonWidget widget) {
            PresetButton.this.onPress.run();
        }

    }

}
