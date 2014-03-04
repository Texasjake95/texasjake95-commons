package com.texasjake95.commons.util.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.common.collect.Lists;

public class ThreadedOutput implements Runnable {
	
	BufferedReader bri;
	final CustomProcess parent;
	Thread thread;
	public boolean isReady = false;
	public boolean isWaiting = false;
	private ArrayList<String> printed = Lists.newArrayList();
	
	public ThreadedOutput(InputStream is, CustomProcess parent)
	{
		bri = new BufferedReader(new InputStreamReader(is));
		this.parent = parent;
		this.thread = new Thread(this);
		this.thread.setDaemon(true);
		this.thread.start();
	}
	
	@Override
	public void run()
	{
		String line;
		try
		{
			while (!parent.isFinished)
			{
				line = bri.readLine();
				if (line != null)
				{
					if (this.parent.shouldPrint)
						System.out.println(line);
					this.printed.add(line);
					this.isReady = false;
					this.isWaiting = false;
				}
				else if (line == null)
				{
					this.isReady = true;
					this.isWaiting = true;
				}
				line = null;
			}
			bri.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.thread.interrupt();
	}
	
	public ArrayList<String> getLinesPrinted()
	{
		return this.printed;
	}
}
