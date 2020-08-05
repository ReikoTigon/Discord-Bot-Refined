package de.dragoncoder.dragonbot.managers;

import de.dragoncoder.dragonbot.hibernate.dao.BotGuildDAO;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;

import java.util.concurrent.ConcurrentHashMap;

public class GuildManager {
    // --Commented out by Inspection (05.08.2020 21:38):private static final Logger logger = LoggerFactory.getLogger(GuildManager.class);
    public final ConcurrentHashMap<Long, BotGuild> guildManager;

    public GuildManager() {
        this.guildManager = new ConcurrentHashMap<>();
    }

    public BotGuild getGuild(long guildID) {
        BotGuild guild;

        if (guildManager.containsKey(guildID)) {
            guild = guildManager.get(guildID);
        }
        else {
            guild = new BotGuildDAO().getByGuildID(guildID);

            if (guild == null) {
                guild = new BotGuild(guildID);
                new BotGuildDAO().save(guild);
            }

            guild.loadDiscordData();

            guildManager.put(guild.getGuild_ID(), guild);
        }

        return guild;
    }
}
