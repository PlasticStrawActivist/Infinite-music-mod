package dev.plasticstraw.inf_music.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.MusicSound;

@Mixin(MusicTracker.class)
public class DisableMusic {

    @Shadow
    private SoundInstance current;

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;tick()V", at = @At("HEAD"),
            cancellable = true)
    private void disable(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        MusicSound musicSound = client.getMusicType();

        if (!musicSound.enabled()) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "Lnet/minecraft/client/sound/MusicTracker;play(Lnet/minecraft/sound/MusicSound;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"))
    private void pauseForMusicMusic(SoundManager soundManager, SoundInstance sound) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (InfiniteMusic.CONFIG.pauseForDiscMusic) {
            // TODO: make this more efficient so its not called very tick
            String debugString = client.getSoundManager().getDebugString();
            if (debugString.charAt(debugString.length() - 3) != '0') {
                return;
            }
        }

        InfiniteMusic.musicInstance = this.current;
        soundManager.play(sound);
    }
}
