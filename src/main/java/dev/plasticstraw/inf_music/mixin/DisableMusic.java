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

    // @Shadow
    // private SoundInstance current;

    // @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;tick()V", at =
    // @At("HEAD"),
    // cancellable = true)
    // private void disable(CallbackInfo ci) {
    // MinecraftClient client = MinecraftClient.getInstance();
    // MusicSound musicSound = client.getMusicType();

    // if (!musicSound.enabled()) {
    // ci.cancel();
    // }
    // }

    // @Redirect(method = "Lnet/minecraft/client/sound/MusicTracker;tick()V",
    // at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0))
    // private int resetTimeUntilNextSong(int arg1, int arg2) {
    // return arg2;
    // }

    // @Redirect(
    // method =
    // "Lnet/minecraft/client/sound/MusicTracker;play(Lnet/minecraft/sound/MusicSound;)V",
    // at = @At(value = "INVOKE",
    // target =
    // "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"))
    // private void pauseForMusicMusic(SoundManager soundManager, SoundInstance
    // sound) {
    // if (InfiniteMusic.CONFIG.pauseForDiscMusic &&
    // InfiniteMusic.isMusicDiscMusicPlaying()) {
    // return;
    // }

    // InfiniteMusic.musicInstance = this.current;
    // soundManager.play(sound);
    // }

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
