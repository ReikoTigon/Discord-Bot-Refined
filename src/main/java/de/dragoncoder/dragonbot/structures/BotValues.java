package de.dragoncoder.dragonbot.structures;

import lombok.Data;

@Data
public class BotValues {
	private boolean init;
	private boolean shutdown;
	private boolean totalShutdown;
	
	public BotValues() {
		setInit(true);			    	//hasStarted
		setShutdown(false);				//shutdown
		setTotalShutdown(false);	    //totalShutdown
	}
}
