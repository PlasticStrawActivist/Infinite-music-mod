package dev.plasticstraw.inf_music.mixin;

import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.plasticstraw.inf_music.util.MusicSoundInterface;

@Mixin(MusicSound.class)
public class MusicDelay implements MusicSoundInterface {

    private int customMinDelay;
    private int customMaxDelay;

    private boolean enabled;

    @Inject(method = "Lnet/minecraft/sound/MusicSound;getMinDelay()I", at = @At("HEAD"), cancellable = true)
    private void getMinDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.customMinDelay);
    }

    @Inject(method = "Lnet/minecraft/sound/MusicSound;getMaxDelay()I", at = @At("HEAD"), cancellable = true)
    private void getMaxDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.customMaxDelay);
    }

    @Override
    public void updateMusicDelays(int minDelay, int maxDelay, boolean enabled) {
        this.customMinDelay = minDelay;
        this.customMaxDelay = maxDelay;
        this.enabled = enabled;
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

}
