package de.dragoncoder.dragonbot.listeners;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import de.dragoncoder.dragonbot.structures.CommandType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class TextListener extends ListenerAdapter {

    // --Commented out by Inspection (05.08.2020 21:40):private static final Logger logger = LoggerFactory.getLogger(TextListener.class);

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        onGuildMessage(event.getGuild().getIdLong(), event.getMessage());
    }

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        onGuildMessage(event.getGuild().getIdLong(), event.getMessage());
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        onPrivateMessage(event.getMessage());
    }

    @Override
    public void onPrivateMessageUpdate(@NotNull PrivateMessageUpdateEvent event) {
        onPrivateMessage(event.getMessage());
    }


    private void onGuildMessage(long guild_ID, Message message) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(guild_ID);

        if (message.getTextChannel().getIdLong() == guild.getBotChannel_ID()) {
            Command_Listener.performCommand(guild, message, CommandType.BOTCHANNEL);
        } else {
            Command_Listener.performCommand(guild, message, CommandType.NORMAL);
        }
    }

    private void onPrivateMessage(Message message) {
        Command_Listener.performPrivateCommand(message, CommandType.PRIVATE);
    }
}
