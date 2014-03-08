package com.texasjake95.commons.event;

/**
 * This class is a copy paste of the Minecraft Forge ASM Event System it just
 * works too well!
 * 
 * @see <a href=" https://github.com/MinecraftForge/MinecraftForge">
 *      https://github.com/MinecraftForge/MinecraftForge</a>
 * 
 */
public enum EventPriority
{
	/*
	 * Priority of event listeners, listeners will be sorted with respect to
	 * this priority level. Note: Due to using a ArrayList in the ListenerList,
	 * these need to stay in a contiguous index starting at 0. {Default ordinal}
	 */
	HIGHEST, // First to execute
	HIGH, NORMAL, LOW, LOWEST // Last to execute
}