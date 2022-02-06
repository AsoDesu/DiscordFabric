package net.asodev.discordfabric.discord;

import net.asodev.discordfabric.server.DiscordFabricServer;
import net.asodev.discordfabric.util.Utils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.transformer.Config;

import java.util.UUID;

import static net.asodev.discordfabric.server.DiscordFabricServer.*;

public class Listener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getChannel().equals(channel)) return;
        if (event.getAuthor().isBot()) return;

        DiscordFabricServer.playerManager.broadcast(
                Text.of("[§bDiscord§f] " + event.getMember().getEffectiveName() + ": " + event.getMessage().getContentRaw()),
                MessageType.SYSTEM, UUID.randomUUID());
    }

    @Override
    public void onReady(@NotNull ReadyEvent e) {
        guild = jda.getGuildById(guildId);
        if (guild == null) {
            Log.error(LogCategory.ENTRYPOINT, "Failed to find guild! Please make sure your Guild ID is correct, restart the server once corrected.");
            return;
        }

        channel = (MessageChannel) guild.getGuildChannelById(channelId);
        if (channel == null) {
            Log.error(LogCategory.ENTRYPOINT, "Failed to find channel! Please make sure your Channel ID is correct, restart the server once corrected.");
            return;
        }

        channel.sendMessage(":white_check_mark: **Server has started!**").queue();
    }

}
