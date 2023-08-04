package dev.plasticstraw.inf_music;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import dev.plasticstraw.inf_music.screen.InfiniteMusicOptions;
import dev.plasticstraw.inf_music.screen.ModMenuScreenFactory;

public class ModMenuImpl implements ModMenuApi {

    public ConfigScreenFactory<InfiniteMusicOptions> getModConfigScreenFactory() {
        return new ModMenuScreenFactory();
    }

}
