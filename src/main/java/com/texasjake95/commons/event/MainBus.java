package com.texasjake95.commons.event;

public class MainBus {
	
	public static final EventBus<SubscribeEvent> BUS = new EventBus<SubscribeEvent>(SubscribeEvent.class);
}
