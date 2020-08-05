package de.dragoncoder.dragonbot.listeners;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.dao.BotGuildDAO;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Command_Listener {

    private static final Logger logger = LoggerFactory.getLogger(Command_Listener.class);

    private static final String prefix = "#d ";
    private static int subString;

        public static void performIfCommand(BotGuild guild, Message msg) {
            String guildPrefix = guild.getPrefix();
            String message = msg.getContentDisplay();

            if (startsWithPrefix(message, guildPrefix)) {
                if (message.length() > subString) {
                    String command = message.substring(subString).split(" ")[0];

                    //noinspection StatementWithEmptyBody
                    if (Main.getDragonBot().getCommandManager().perform(command, msg, subString)) {
                        guild.addToCommandsUsed();

                        new BotGuildDAO().update(guild);
                    }
                    else {
                        //TODO: Error to Discord
                    }
                }
            }
        }

    private static boolean startsWithPrefix(String message, String guildPrefix) {
        boolean startsWithPrefix = false;
        logger.info("Test: " + message);
        logger.info("Test: " + guildPrefix);

        if (message.length() > 0) {
            if (message.startsWith(prefix)) {
                subString = prefix.length();
                startsWithPrefix = true;
            } else if (guildPrefix != null && message.startsWith(guildPrefix)) {
                subString = guildPrefix.length();
                startsWithPrefix = true;
            }
        }

        return startsWithPrefix;
    }

}
