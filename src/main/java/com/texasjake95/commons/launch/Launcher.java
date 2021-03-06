package com.texasjake95.commons.launch;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

import com.texasjake95.commons.asm.ASMClassLoader;

public class Launcher {
	
	public static ASMClassLoader loader;
	
	public Launcher()
	{
		final URLClassLoader ucl = (URLClassLoader) getClass().getClassLoader();
		loader = new ASMClassLoader(ucl.getURLs());
	}
	
	public static void main(String args[])
	{
		new Launcher().launch(args);
	}
	
	private void launch(String[] args)
	{
		try
		{
			loader.registerTransformer("com.texasjake95.commons.event.EventTransformer");
			if (args.length > 1)
			{
				String[] exlude = args[1].split(";");
				for (String e : exlude)
					loader.addClassLoaderExclusion(e);
			}
			final Class<?> clazz = Class.forName(args[0], false, loader);
			final Method mainMethod = clazz.getMethod("main", new Class[] { String[].class });
			mainMethod.invoke(null, (Object) args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
