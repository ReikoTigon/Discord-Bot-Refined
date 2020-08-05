package de.dragoncoder.dragonbot;

import de.dragoncoder.dragonbot.listeners.TextListener;
import de.dragoncoder.dragonbot.managers.CommandManager;
import de.dragoncoder.dragonbot.managers.GuildManager;
import de.dragoncoder.dragonbot.structures.BotValues;
import de.dragoncoder.dragonbot.utils.BotUtils;
import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;

public class Bot {

    private final Logger logger;

    @Getter
    private final ShardManager shardMan;
    @Getter
    private final GuildManager guildManager;
    @Getter
    private final CommandManager commandManager;

    @Getter
    private final BotValues botValues;

    private final BotUtils botUtils;

    public Bot () {
        logger = LoggerFactory.getLogger(Bot.class);

        botValues = new BotValues();
        botUtils = new BotUtils(botValues);

        shardMan = createShardManager();
        guildManager = new GuildManager();
        commandManager = new CommandManager();

        awaitJdaReady();

        logger.info("Bot online");

        runConsoleListener();
    }

    private ShardManager createShardManager() {
        ShardManager shardMan;
        EnumSet<GatewayIntent> Intents = EnumSet.of(GatewayIntent.GUILD_MEMBERS,
                                     GatewayIntent.GUILD_BANS,
                                     GatewayIntent.GUILD_EMOJIS,
                                     GatewayIntent.GUILD_VOICE_STATES,
                                     GatewayIntent.GUILD_MESSAGES,
                                     GatewayIntent.GUILD_MESSAGE_REACTIONS,
                                     GatewayIntent.DIRECT_MESSAGES);

        try {
            DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.create(Main.getSecuredData().getToken(), Intents);
            builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS);
            builder.setStatus(OnlineStatus.ONLINE);

            //EventListeners
            builder.addEventListeners(new TextListener());

            shardMan = builder.build();
        }
        catch (LoginException e) {
            shardMan = null;
            logger.error("Error whilst connecting to Discord", e);
        }
        return shardMan;
    }
    private void awaitJdaReady() {
        shardMan.getShards().forEach(jda -> {
            try {
                jda.awaitReady();
            } catch (InterruptedException e) {
                logger.error("Error whilst startup: ", e);
            }
        });
    }

    private void runConsoleListener() {
        Thread consoleListener = new Thread(() -> {

            try {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                while((line = reader.readLine()) != null) {

                    botUtils.callAction(line);
                    if (botValues.isTotalShutdown()) {
                        reader.close();
                    }
                }
            } catch (IOException ignored){ }

        });

        consoleListener.setName("ConsoleListener");
        consoleListener.start();
    }
}
