package com.texasjake95.commons.util.system;

public class OS {
	
	public enum Platform
	{
		Windows("Windows"), Mac("Mac"), Unix("Unix"), Solaris("Solaris"), unsupported("Unsupported");
		
		public final String name;
		
		Platform(String name)
		{
			this.name = name;
		}
	}
	
	private static Platform m_os = null;
	
	public static Platform getOS()
	{
		if (m_os == null)
		{
			String os = System.getProperty("os.name").toLowerCase();
			m_os = Platform.unsupported;
			if (os.indexOf("win") >= 0)
				m_os = Platform.Windows;
			if (os.indexOf("mac") >= 0)
				m_os = Platform.Mac;
			if (os.indexOf("nux") >= 0 || os.indexOf("nix") >= 0 || os.indexOf("aix") > 0)
				m_os = Platform.Unix;
			if (os.indexOf("sunos") >= 0)
				m_os = Platform.Solaris;
		}
		return m_os;
	}
	
	public static boolean isWindows()
	{
		return (getOS() == Platform.Windows);
	}
	
	public static boolean isMac()
	{
		return (getOS() == Platform.Mac);
	}
	
	public static boolean isUnix()
	{
		return (getOS() == Platform.Unix);
	}
	
	public static boolean isSolaris()
	{
		return (getOS() == Platform.Solaris);
	}
}