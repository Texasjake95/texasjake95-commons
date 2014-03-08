package com.texasjake95.commons.event;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

/**
 * This class is a copy paste of the Minecraft Forge ASM Event System it just
 * works too well!
 * 
 * @see <a href=" https://github.com/MinecraftForge/MinecraftForge">
 *      https://github.com/MinecraftForge/MinecraftForge</a>
 * 
 */
@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface SubscribeEvent
{
	
	public EventPriority priority() default EventPriority.NORMAL;
	
	public boolean receiveCanceled() default false;
}