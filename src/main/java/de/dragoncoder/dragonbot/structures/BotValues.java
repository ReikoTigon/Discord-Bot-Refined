package de.dragoncoder.dragonbot.structures;

import lombok.Data;

@Data
public class BotValues {
	private boolean init;
	private boolean shutdown;
	
	public BotValues() {
		setInit(true);			    	//hasStarted
		setShutdown(false);				//shutdown
	}
}
