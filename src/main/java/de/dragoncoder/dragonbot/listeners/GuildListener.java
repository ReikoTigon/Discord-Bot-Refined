package de.dragoncoder.dragonbot.listeners;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.dao.BotGuildDAO;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {

    // --Commented out by Inspection (05.08.2020 21:40):private static final Logger logger = LoggerFactory.getLogger(TextListener.class);

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(event.getGuild().getIdLong());

        if (!guild.isJoined()) {
            guild.setJoined(true);
            new BotGuildDAO().update(guild);
        }
        else {
            new BotGuildDAO().save(guild);
        }
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(event.getGuild().getIdLong());

        guild.setJoined(false);
        new BotGuildDAO().update(guild);

    }
}
