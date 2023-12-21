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
                this.height - 64,
                32,
                25);
        this.initWidgets();

        this.addDrawableChild(optionButtons);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close())
                .dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void close() {
        InfiniteMusic.CONFIG.write();
        InfiniteMusic.updateMusicDelays();
        this.client.setScreen(this.parent);
    }

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }

}
