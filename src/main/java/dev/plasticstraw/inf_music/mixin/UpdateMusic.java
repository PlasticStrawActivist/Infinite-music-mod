package dev.plasticstraw.inf_music.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.plasticstraw.inf_music.InfiniteMusic;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.world.ClientWorld;

@Mixin(MinecraftClient.class)
public class UpdateMusic {

    @Inject(method = "Lnet/minecraft/client/MinecraftClient;joinWorld(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen$WorldEntryReason;)V", at = @At("HEAD"), cancellable = true)
    private void updateMusicOnWorldJoin(ClientWorld world, DownloadingTerrainScreen.WorldEntryReason worldEntryReason,
            CallbackInfo ci) {
        InfiniteMusic.updateMusicDelays();
        InfiniteMusic.TRACKER.hasJoinedWorld();
    }

}
