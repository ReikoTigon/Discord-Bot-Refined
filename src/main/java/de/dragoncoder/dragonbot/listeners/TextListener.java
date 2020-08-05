package de.dragoncoder.dragonbot.listeners;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class TextListener extends ListenerAdapter {

    // --Commented out by Inspection (05.08.2020 21:40):private static final Logger logger = LoggerFactory.getLogger(TextListener.class);

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(event.getGuild().getIdLong());

        switch ((int) event.getGuild().getIdLong()) {
            case 1:
                //Call Listener 1
                break;
            case 2:
                //Call Listener 2
                break;
            default:
                Command_Listener.performIfCommand(guild, event.getMessage());
                break;
        }


    }

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(event.getGuild().getIdLong());

        switch ((int) event.getGuild().getIdLong()) {
            case 1:
                //Call Listener 1
                break;
            case 2:
                //Call Listener 2
                break;
            default:
                Command_Listener.performIfCommand(guild, event.getMessage());
                break;
        }
    }
}
