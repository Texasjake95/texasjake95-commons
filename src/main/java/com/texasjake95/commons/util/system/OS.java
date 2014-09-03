package com.texasjake95.commons.util.system;

public class OS {

	public enum Platform
	{
		Windows("Windows", "win"),
		Mac("Mac", "mac"),
		Unix("Unix", "nux", "nix", "aix"),
		Solaris("Solaris", "sunos"),
		unsupported("Unsupported");

		public final String name;
		public final String[] matches;

		Platform(String name, String... matches)
		{
			this.name = name;
			this.matches = matches;
		}

		public boolean isValid(String os)
		{
			for (String match : this.matches)
				if (os.contains(match.toLowerCase()))
					return true;
			return false;
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}

	private static Platform m_os = null;

	public static Platform getOS()
	{
		if (m_os == null)
		{
			String os = System.getProperty("os.name", "generic").toLowerCase();
			m_os = Platform.unsupported;
			for (Platform platform : Platform.values())
				if (platform.isValid(os))
					m_os = platform;
		}
		return m_os;
	}

	public static boolean isMac()
	{
		return getOS() == Platform.Mac;
	}

	public static boolean isSolaris()
	{
		return getOS() == Platform.Solaris;
	}

	public static boolean isUnix()
	{
		return getOS() == Platform.Unix;
	}

	public static boolean isWindows()
	{
		return getOS() == Platform.Windows;
	}
}
