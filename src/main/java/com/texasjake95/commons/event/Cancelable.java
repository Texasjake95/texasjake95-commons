package com.texasjake95.commons.event;


/**
 * This class is a copy paste of the Minecraft Forge ASM Event System it just
 * works too well!
 * 
 * @see <a href=" https://github.com/MinecraftForge/MinecraftForge">
 *      https://github.com/MinecraftForge/MinecraftForge</a>
 * 
 */
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = RUNTIME)
@Target(value = TYPE)
public @interface Cancelable{}