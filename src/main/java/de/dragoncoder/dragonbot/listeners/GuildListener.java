package de.dragoncoder.dragonbot.listeners;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.dao.BotGuildDAO;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import de.dragoncoder.dragonbot.utils.SendingUtils;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {

    // --Commented out by Inspection (05.08.2020 21:40):private static final Logger logger = LoggerFactory.getLogger(TextListener.class);
    private static final String botChannelTopic = "The default Channel to manage the bot. " +
                                                  "\n(Can be renamed. Please don't delete it.) " +
                                                  "\nIssues with the bot? Go to GitHub -> https://github.com/ReikoTigon/Discord-Bot-Refined/issues";
    private static final String helloMessage = "Hello, \n" +
                                               "I'm DragonBot. A bot capable of various tasks. \n" +
                                               " \n" +
                                               "See '#d startup' to get some Instructions on how to work with me. \n" +
                                               "See '#d help' to get some help :D \n" +
                                               " \n" +
                                               "Issues with the bot? Go to GitHub -> https://github.com/ReikoTigon/Discord-Bot-Refined/issues \n" +
                                               "or message Reiko Tigon#3233";

    private static final String channelInfo = "This is the channel I will send information to. \n" +
                                              "This also the only channel you can configure me trough, \n" +
                                              "so make sure this channel always exists. \n" +
                                              "Please restrict access to this channel by roles, so nobody without permission can configure me ;) \n" +
                                              " \n" +
                                              "If you accidentally delete this channel you can always use '#d restore' to recreate all structures needed by the bot.";



    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(event.getGuild().getIdLong());

        guild.activate();

        guild.getGuild().createCategory("DragonBot")
                        .queue(category -> category.createTextChannel("BotChannel")
                                                   .setTopic(botChannelTopic)
                                                   .queue(textChannel ->  {

            guild.setBotChannel_ID(textChannel.getIdLong());

            new BotGuildDAO().update(guild);

            guild.loadBotChannel();

            SendingUtils.sendNotification(textChannel, helloMessage);
            SendingUtils.sendNotification(textChannel, channelInfo);
        }));
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        BotGuild guild = Main.getDragonBot().getGuildManager().getGuild(event.getGuild().getIdLong());

        guild.deactivate();
    }



}
