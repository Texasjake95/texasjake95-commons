package com.texasjake95.commons.util.system;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.texasjake95.commons.helpers.Checker;

public class ThreadedInput implements Runnable {
	
	BufferedWriter brW;
	final CustomProcess parent;
	Thread thread;
	public boolean isReady = false;
	final ThreadedOutput in;
	final ThreadedOutput out;
	String[] inputs;
	
	public ThreadedInput(OutputStream os, CustomProcess parent, ThreadedOutput inputStream, ThreadedOutput errorSteam, String[] inputs)
	{
		brW = new BufferedWriter(new OutputStreamWriter(os));
		this.parent = parent;
		this.in = inputStream;
		this.out = errorSteam;
		this.inputs = inputs;
		this.thread = new Thread(this);
		this.thread.setDaemon(true);
		this.thread.start();
	}
	
	@Override
	public void run()
	{
		if (inputs.length == 0)
		{
			this.isReady = true;
			this.thread.interrupt();
		}
		int index = 0;
		try
		{
			while (!parent.isFinished)
			{
				if (Checker.areAllTrue(in.isWaiting, out.isWaiting, index < this.inputs.length))
				{
					this.brW.write(this.inputs[index]);
					index++;
				}
				this.isReady = index >= this.inputs.length;
			}
			this.isReady = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.thread.interrupt();
	}
}
