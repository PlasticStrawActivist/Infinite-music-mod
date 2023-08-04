package dev.plasticstraw.inf_music.screen;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuScreenFactory implements ConfigScreenFactory<InfiniteMusicOptions> {

    @Override
    public InfiniteMusicOptions create(Screen parent) {
        return new InfiniteMusicOptions(parent);
    }

}
