package dev.plasticstraw.inf_music.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;

@Mixin(SoundManager.class)
public class CancelStartMusic {

    private boolean canceledStartMusic = false;

    @Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    private void cancel(SoundInstance sound, CallbackInfo ci) {
        if (!canceledStartMusic && sound.getId().getPath() == "music.menu") {
            canceledStartMusic = true;
            ci.cancel();
        }
    }
}
