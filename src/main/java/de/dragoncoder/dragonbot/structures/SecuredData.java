package de.dragoncoder.dragonbot.structures;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class SecuredData {

    private final String token; //Discord Application Token
    
}
