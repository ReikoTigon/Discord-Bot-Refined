package de.dragoncoder.dragonbot.managers;

import de.dragoncoder.dragonbot.utils.ServerCommand;
import net.dv8tion.jda.api.entities.Message;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    // --Commented out by Inspection (05.08.2020 21:01):private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);
    public final ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();
//        this.commands.put("dragon", new Help());

    }

    public boolean perform(String command, Message message, int subString) {

        ServerCommand serverCommand;
        if((serverCommand = this.commands.get(command.toLowerCase())) != null) {
            serverCommand.performCommand(message, subString);
            return true;
        }

        return false;
    }
}
