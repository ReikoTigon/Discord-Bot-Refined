package de.dragoncoder.dragonbot.listeners;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.dao.BotGuildDAO;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import de.dragoncoder.dragonbot.structures.CommandType;
import de.dragoncoder.dragonbot.utils.SendingUtils;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class Command_Listener {

    private static final Logger logger = LoggerFactory.getLogger(Command_Listener.class);

    private static final ArrayList<String> nonSpecificCommands;
    private static final ArrayList<String> botChannelCommands;
    private static final ArrayList<String> privateCommands;

    private static final String prefix = "#d ";
    private static int subString;

    static {
        //noinspection ArraysAsListWithZeroOrOneArgument
        nonSpecificCommands = new ArrayList<>(Arrays.asList());
        //noinspection ArraysAsListWithZeroOrOneArgument
        botChannelCommands = new ArrayList<>(Arrays.asList());
        //noinspection ArraysAsListWithZeroOrOneArgument
        privateCommands = new ArrayList<>(Arrays.asList());
    }

    public static void performCommand(BotGuild guild, Message msg, CommandType type) {
        String guildPrefix = guild.getPrefix();
        String message = msg.getContentDisplay();

        if (startsWithPrefix(message, guildPrefix)) {
            if (message.length() > subString) {
                String command = message.substring(subString).split(" ")[0];

                if (validateCommand(type, command)) {
                    if (Main.getDragonBot().getCommandManager().perform(command, msg, subString)) {
                        guild.addToCommandsUsed();

                        new BotGuildDAO().update(guild);
                    } else {
                        String errorMessage = "This is no valid command! \n" +
                                              "Use '#d help' to see the list of commands. \n" +
                                              "If you're still having issues report them on [Github](https://github.com/ReikoTigon/Discord-Bot-Refined/issues)";
                        SendingUtils.sendError(msg.getChannel(), errorMessage);
                    }
                } else {
                    String errorMessage = "This command is not allowed in this channel! \n" +
                                          "Use '#d help" + command + "' to see information to the command. \n" +
                                          "If you're still having issues report them on [Github](https://github.com/ReikoTigon/Discord-Bot-Refined/issues)";
                    SendingUtils.sendError(msg.getChannel(), errorMessage);
                }
            }
        }
    }

    public static void performPrivateCommand(Message msg, CommandType type) {
        String message = msg.getContentDisplay();

        if (message.startsWith(prefix)) {
            if (message.length() > subString) {
                String command = message.substring(subString).split(" ")[0];

                if (validateCommand(type, command)) {
                    if (!Main.getDragonBot().getCommandManager().perform(command, msg, subString)) {
                        String errorMessage = "This is no valid command! \n" +
                                "Use '#d help' to see the list of commands. \n" +
                                "If you're still having issues report them on [Github](https://github.com/ReikoTigon/Discord-Bot-Refined/issues)";
                        SendingUtils.sendError(msg.getChannel(), errorMessage);
                    }
                } else {
                    String errorMessage = "This command is not allowed in this channel! \n" +
                            "Use '#d help" + command + "' to see information to the command. \n" +
                            "If you're still having issues report them on [Github](https://github.com/ReikoTigon/Discord-Bot-Refined/issues)";
                    SendingUtils.sendError(msg.getChannel(), errorMessage);
                }
            }
        }
    }

    private static boolean startsWithPrefix(String message, String guildPrefix) {
        if (message.length() > 0) {
            if (message.startsWith(prefix)) {
                subString = prefix.length();
                return true;
            } else if (guildPrefix != null && message.startsWith(guildPrefix)) {
                subString = guildPrefix.length();
                return true;
            }
        }

        return false;
    }
    private static boolean validateCommand(CommandType type, String command) {
        boolean isValidCommand = false;

        switch (type) {

            case NORMAL:
                for (String cmd : nonSpecificCommands) {
                    if (command.equals(cmd)) {
                        isValidCommand = true;
                        break;
                    }
                }
                break;
            case BOTCHANNEL:
                for (String cmd : botChannelCommands) {
                    if (command.equals(cmd)) {
                        isValidCommand = true;
                        break;
                    }
                }
                break;
            case PRIVATE:
                for (String cmd : privateCommands) {
                    if (command.equals(cmd)) {
                        isValidCommand = true;
                        break;
                    }
                }
                break;
        }

        return isValidCommand;
    }
}
