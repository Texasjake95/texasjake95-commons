package com.texasjake95.commons.event;

public class MainBus {
	
	public static final EventBus<Event> BUS = new EventBus<Event>(Event.class);
}
