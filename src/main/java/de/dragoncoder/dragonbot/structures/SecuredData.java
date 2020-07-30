package de.dragoncoder.dragonbot.structures;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class SecuredData {

    private final String token; //Discord Application Token
    private final String link; //Invite Link of the bot
    private final String mysqlLink; //Link to the Bot MySQL Database
    private final String mysqlUser; //User for the Databases
    private final String mysqlPswd; //Password for the databases
    
}
