package com.texasjake95.commons.asm;

/**
 * This class is a copy paste of the Minecraft's Launcher Wrapper (Which is Open Sources) created by CPW
 * used to allow runtime class changes
 * 
 * @see <a href=" https://github.com/Mojang/LegacyLauncher">
 *      https://github.com/Mojang/LegacyLauncher</a>
 * 
 */
public interface IClassTransformer {

	byte[] transform(String name, byte[] basicClass);
}
