package de.dragoncoder.dragonbot.utils;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.HibernateUtils;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import de.dragoncoder.dragonbot.structures.BotValues;
import net.dv8tion.jda.api.OnlineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotUtils {

    private final Logger logger;

    private final BotValues botValues;

    public BotUtils(BotValues botValues) {
        logger = LoggerFactory.getLogger(BotUtils.class);
        this.botValues = botValues;
    }

    public void callAction(String line) {

        switch (line.toLowerCase().split(" ")[0]) {
            case "exit":
                onShutdown();
                botValues.setShutdown(true);
                break;
//            case "stats":
//                stats();
//                break;
            case "notify":
                notify(line.substring(7));
                break;
            default:
                logger.info("Unknown Command");
                break;
        }
    }

    private void notify(String message) {
        Main.getDragonBot().getShardManager().getGuilds().forEach(guild -> {
            BotGuild botGuild = Main.getDragonBot().getGuildManager().getGuild(guild.getIdLong());
            botGuild.loadBotChannel();

            if (botGuild.getBotChannel() != null) {
                SendingUtils.sendNotification(botGuild.getBotChannel(), message);
            }
        });
    }

    private void onShutdown() {
        HibernateUtils.shutdown();

        Main.getDragonBot().getShardManager().setStatus(OnlineStatus.OFFLINE);
        Main.getDragonBot().getShardManager().shutdown();

        logger.info("Bot Offline.");
    }
}
