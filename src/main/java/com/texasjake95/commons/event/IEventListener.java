package com.texasjake95.commons.event;

/**
 * This class is a copy paste of the Minecraft Forge ASM Event System it just
 * works too well!
 * 
 * @see <a href=" https://github.com/MinecraftForge/MinecraftForge">
 *      https://github.com/MinecraftForge/MinecraftForge</a>
 * 
 */
public interface IEventListener {
	
	public void invoke(Event event);
}