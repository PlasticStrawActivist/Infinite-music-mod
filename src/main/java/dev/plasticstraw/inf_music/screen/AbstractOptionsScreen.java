package dev.plasticstraw.inf_music.screen;

import org.jetbrains.annotations.Nullable;

import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public abstract class AbstractOptionsScreen extends GameOptionsScreen {

    @Nullable
    private ClickableWidget narratorButton;
    protected OptionListWidget optionButtons;

    @SuppressWarnings("resource")
    public AbstractOptionsScreen(Screen parent, String translatableKey) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable(translatableKey));
    }

    protected abstract SimpleOption<?>[] getWidgets();

    protected void initScreen() {
        return;
    }

    @Override
    protected final void init() {
        optionButtons = addDrawableChild(new OptionListWidget(client, width, height, this));
        optionButtons.addAll(getWidgets());
        initScreen();
        narratorButton = optionButtons.getWidgetFor(gameOptions.getNarrator());
        if (narratorButton != null) {
            narratorButton.active = client.getNarratorManager().isActive();
        }
        super.init();
    }

    @Override
    public void close() {
        InfiniteMusic.CONFIG.write();
        InfiniteMusic.updateMusicDelays();
        client.setScreen(parent);
    }

    @Override
    protected void initTabNavigation() {
        super.initTabNavigation();
        optionButtons.position(this.width, this.layout);
    }

}
