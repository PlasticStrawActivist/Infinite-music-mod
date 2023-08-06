package dev.plasticstraw.inf_music.screen;

import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public abstract class AbstractOptionsScreen extends GameOptionsScreen {

    protected OptionListWidget optionButtons;

    @SuppressWarnings("resource")
    public AbstractOptionsScreen(Screen parent, String translatbleKey) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable(translatbleKey));
    }

    protected abstract void initWidgets();

    @Override
    public final void init() {
        this.optionButtons = new OptionListWidget(
                this.client,
                this.width,
                this.height,
                32,
                this.height - 32,
                25);
        this.initWidgets();

        this.addSelectableChild(optionButtons);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close())
                .dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void close() {
        InfiniteMusic.LOGGER.info("closed");
        InfiniteMusic.CONFIG.write();
        InfiniteMusic.updateMusicDelays();
        this.client.setScreen(this.parent);
    }

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.render(context, this.optionButtons, mouseX, mouseY, delta);
    }

}
