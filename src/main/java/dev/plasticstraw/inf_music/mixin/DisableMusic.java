package dev.plasticstraw.inf_music.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.sound.MusicSound;

@Mixin(MusicTracker.class)
public class DisableMusic {

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;tick()V", at = @At("HEAD"), cancellable = true)
    private void redirectTick(CallbackInfo ci) {
        ci.cancel();
        InfiniteMusic.TRACKER.tick();
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;play(Lnet/minecraft/sound/MusicSound;)V", at = @At("HEAD"), cancellable = true)
    private void redirectPlay(MusicSound musicSound, CallbackInfo ci) {
        ci.cancel();
        InfiniteMusic.TRACKER.play(musicSound);
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;stop(Lnet/minecraft/sound/MusicSound;)V", at = @At("HEAD"), cancellable = true)
    private void redirectStop(MusicSound musicSound, CallbackInfo ci) {
        ci.cancel();
        InfiniteMusic.TRACKER.stop(musicSound);
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;stop()V", at = @At("HEAD"), cancellable = true)
    private void redirectStop(CallbackInfo ci) {
        ci.cancel();
        InfiniteMusic.TRACKER.stop();
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;isPlayingType(Lnet/minecraft/sound/MusicSound;)Z", at = @At("HEAD"), cancellable = true)
    private void redirectIsPlayingType(MusicSound musicSound, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue(InfiniteMusic.TRACKER.isPlayingType(musicSound));
    }

}
