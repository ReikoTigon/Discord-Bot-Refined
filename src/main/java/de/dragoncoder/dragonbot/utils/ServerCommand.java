package de.dragoncoder.dragonbot.utils;

import net.dv8tion.jda.api.entities.Message;

public interface ServerCommand {

    void performCommand(Message message,  int subString);

}