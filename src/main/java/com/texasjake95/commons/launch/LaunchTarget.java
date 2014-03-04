package com.texasjake95.commons.launch;

public abstract class LaunchTarget {
	
	protected String userName = "";
	protected boolean launch = false;
	
	public abstract void runTarget();
	
	public abstract String getTargetName();
	
	@Deprecated
	public abstract String getDependencies();
	
	public void setShouldLaunch(boolean launch)
	{
		this.launch = launch;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
}
