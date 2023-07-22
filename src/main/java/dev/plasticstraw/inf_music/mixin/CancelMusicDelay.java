package dev.plasticstraw.inf_music.mixin;

import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MusicSound.class)
public class CancelMusicDelay {

    @Inject(method = "Lnet/minecraft/sound/MusicSound;getMaxDelay()I", at = @At("HEAD"),
            cancellable = true)
    private void injected(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

}
