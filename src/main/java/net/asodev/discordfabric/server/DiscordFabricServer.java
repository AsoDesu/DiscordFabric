package net.asodev.discordfabric.server;

import net.asodev.discordfabric.discord.Listener;
import net.asodev.discordfabric.util.SimpleConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.server.PlayerManager;
import javax.security.auth.login.LoginException;

@Environment(EnvType.SERVER)
public class DiscordFabricServer implements DedicatedServerModInitializer {

    public static JDA jda;

    public static Guild guild;
    public static MessageChannel channel;

    public static String guildId;
    public static String channelId;

    public static SimpleConfig Config;
    public static PlayerManager playerManager;

    @Override
    public void onInitializeServer() {
        Config = SimpleConfig.of("discord").provider(this::provider).request();

        Log.info(LogCategory.LOG, "Loading DiscordFabric...");
        try {
            jda = JDABuilder.createDefault(Config.getOrDefault("token", "")).build();
        } catch (LoginException e) {
            Log.error(LogCategory.ENTRYPOINT, "Failed to login to discord! Please make sure your token is correct, restart the server once corrected.");
            return;
        }
        Log.info(LogCategory.ENTRYPOINT, "Successfully connected to discord!");

        guildId = Config.getOrDefault("guildId", "");
        channelId = Config.getOrDefault("channelId", "");

        jda.addEventListener(new Listener());
    }

    private String provider( String filename ) {
        return """
                #Discord Fabric Config
                token=[Discord Token]
                guildId=[Discord Server Id]
                channelId=[Discord Channel Id]
                """;
    }

    public static void sendMsg(String s) {
        channel.sendMessage(s).queue();
    }
}
