package com.texasjake95.commons.event;

public class MainBus {
	
	public static final EventBus<SubscribeEvent, Event> BUS = new EventBus<SubscribeEvent, Event>(SubscribeEvent.class, Event.class);
}
