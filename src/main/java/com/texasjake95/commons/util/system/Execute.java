package com.texasjake95.commons.util.system;


public class Execute {
	
	public static ProcessResult execute(String command, boolean shouldPrint, String... parms)
	{ 
		return execute(command, new String[] {},shouldPrint, parms);
	}
	
	public static ProcessResult execute(String command, String[] laterInput, boolean shouldPrint, String... parms)
	{
		StringBuilder Exec = new StringBuilder();
		if (getSystemExec() != "")
			Exec.append(String.format("%s %s", getSystemExec(), command));
		else
			Exec.append(command);
		for (String parm : parms)
		{
			Exec.append(" " + parm);
		}
		CustomProcess p =	new CustomProcess(Exec.toString(),laterInput,shouldPrint);
		return p.StartAndWait();
	}
	
	public static String getSystemExec()
	{
		String exec = null;
		switch (OS.getOS())
		{
			case Mac:
				exec = "";
				break;
			case Solaris:
				break;
			case Unix:
				break;
			case Windows:
				exec = "cmd /c start";
				break;
			default:
				break;
		}
		return exec;
	}
}
