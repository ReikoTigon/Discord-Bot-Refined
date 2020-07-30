package de.dragoncoder.dragonbot.utils;

import de.dragoncoder.dragonbot.Main;
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
                botValues.setTotalShutdown(true);
                break;
//            case "active":
//                active();
//                break;
//            case "stats":
//                stats();
//                break;
//            case "guilds":
//                DragonBot_Utils.guilds();
//                break;
//            case "members":
//                members(Integer.parseInt(line.substring((line.indexOf(" ") + 1)).split(" ")[0]));
//                break;
//            case "notify":
//                notify(line.substring((line.indexOf(" ") + 1)));
//                break;
            default:
                logger.info("Unknown Command");
                break;
        }
    }

    private void onShutdown() {
        Main.getDragonBot().getShardMan().setStatus(OnlineStatus.OFFLINE);
        Main.getDragonBot().getShardMan().shutdown();

        logger.info("Offline.");
    }
}