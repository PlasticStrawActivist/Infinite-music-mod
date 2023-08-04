package dev.plasticstraw.inf_music.config.widget;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class BooleanButton implements SimpleOptionWidget<Boolean> {

    private final SimpleOption<Boolean> simpleOption;

    public BooleanButton(
            String translationKey,
            Boolean initialValue,
            Function<Boolean, Text> textCallback,
            Consumer<Boolean> callback) {
        this.simpleOption = SimpleOption.ofBoolean(
                translationKey,
                SimpleOption.emptyTooltip(),
                (optionText, value) -> textCallback.apply(value),
                initialValue,
                (value) -> callback.accept(value));
    }

    public SimpleOption<Boolean> getSimpleOption() {
        return this.simpleOption;
    }

}
