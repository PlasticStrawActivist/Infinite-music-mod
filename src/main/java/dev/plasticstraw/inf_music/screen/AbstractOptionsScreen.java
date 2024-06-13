package dev.plasticstraw.inf_music.screen;

import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public abstract class AbstractOptionsScreen extends GameOptionsScreen {

    @SuppressWarnings("resource")
    public AbstractOptionsScreen(Screen parent, String translatableKey) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable(translatableKey));
    }

    protected abstract SimpleOption<?>[] getWidgets();

    @Override
    protected void addOptions() {
        body.addAll(getWidgets());
    }

    @Override
    public void close() {
        InfiniteMusic.CONFIG.write();
        super.close();
    }

}
