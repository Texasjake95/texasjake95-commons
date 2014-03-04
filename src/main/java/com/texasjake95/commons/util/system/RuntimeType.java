package com.texasjake95.commons.util.system;

public class RuntimeType {
	
	public static boolean isJar()
	{
		return System.getProperty("java.class.path").contains("org.eclipse.equinox.launcher");
	}
}
