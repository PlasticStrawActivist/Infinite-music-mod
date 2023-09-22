package dev.plasticstraw.inf_music.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;

@Mixin(SoundManager.class)
public class ValidateSounds {

    private boolean canceledStartMusic = false;

    @Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At("HEAD"), cancellable = true)
    private void validateSounds(SoundInstance soundInstance, CallbackInfo ci) {
        if (!canceledStartMusic && soundInstance.getId().getPath() == "music.menu") {
            canceledStartMusic = true;
            ci.cancel();
        }

        if (soundInstance.getId().getPath().startsWith("music_disc.")) {
            InfiniteMusic.musicDiscInstanceList.add(soundInstance);

            if (InfiniteMusic.CONFIG.pauseForDiscMusic) {
                MinecraftClient.getInstance().getSoundManager().stop(InfiniteMusic.musicInstance);
            }
        }
    }

}
