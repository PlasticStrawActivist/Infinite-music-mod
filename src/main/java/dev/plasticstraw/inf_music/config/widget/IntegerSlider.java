package dev.plasticstraw.inf_music.config.widget;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class IntegerSlider implements SimpleOptionWidget<Integer> {

    private final SimpleOption<Integer> simpleOption;

    public IntegerSlider(
            String translationString,
            int minValue,
            int maxValue,
            int initialValue,
            Function<Integer, Text> textCallback,
            Consumer<Integer> callback) {
        this.simpleOption = new SimpleOption<Integer>(
                translationString,
                SimpleOption.emptyTooltip(),
                (optionText, value) -> Text.translatable(
                        "inf_music.config.slider",
                        optionText,
                        textCallback.apply(value)),
                new SimpleOption.ValidatingIntSliderCallbacks(minValue, maxValue),
                initialValue,
                (value) -> callback.accept(value));
    }

    public SimpleOption<Integer> getSimpleOption() {
        return this.simpleOption;
    }

}
