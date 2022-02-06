package net.asodev.discordfabric.mixins;

import net.asodev.discordfabric.server.DiscordFabricServer;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ChatMixin {

    @Inject(method = "onChatMessage", at = @At("TAIL"))
    private void onChatMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        ServerPlayNetworkHandler self = ((ServerPlayNetworkHandler)(Object)this);
        ServerPlayerEntity player = self.player;
        DiscordFabricServer.sendMsg(player.getName().getString() + ": " + packet.getChatMessage());
    }

}
