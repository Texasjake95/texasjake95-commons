package com.texasjake95.commons.event;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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
@Inherited
public @interface SubscribeEvent
{
	
	public EventPriority priority() default EventPriority.NORMAL;
	
	public boolean receiveCanceled() default false;
}