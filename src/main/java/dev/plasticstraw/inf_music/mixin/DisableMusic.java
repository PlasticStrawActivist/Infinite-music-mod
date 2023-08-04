package dev.plasticstraw.inf_music.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.sound.MusicSound;

@Mixin(MusicTracker.class)
public class DisableMusic {

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;tick()V", at = @At("HEAD"), cancellable = true)
    private void disable(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        MusicSound musicSound = client.getMusicType();

        if (!musicSound.enabled()) {
            ci.cancel();
        }
    }
}
