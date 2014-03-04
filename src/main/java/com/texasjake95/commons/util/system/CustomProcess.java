package com.texasjake95.commons.util.system;

//
import java.io.IOException;

import com.texasjake95.commons.helpers.Checker;

public class CustomProcess {
	
	boolean isFinished = false;
	private String command = "";
	private String[] inputs;
	public boolean shouldPrint;
	
	public CustomProcess(String command, String[] inputs, boolean shouldPrint)
	{
		this.command = command;
		if (inputs != null)
			this.inputs = inputs;
		else
			this.inputs = new String[] {};
		this.shouldPrint = shouldPrint;
	}
	
	public ProcessResult StartAndWait()
	{
		if (!command.equals(""))
			try
			{
				Process p = Runtime.getRuntime().exec(command);
				ThreadedOutput output = new ThreadedOutput(p.getInputStream(), this);
				ThreadedOutput error = new ThreadedOutput(p.getErrorStream(), this);
				ThreadedInput input = new ThreadedInput(p.getOutputStream(), this, output, error, inputs);
				while (!Checker.areAllTrue(output.isReady, error.isReady, input.isReady))
				{
					System.out.print(""); // Needed for some reason to run
											// properly
				}
				p.waitFor();
				this.isFinished = true;
				return new ProcessResult(output, error);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		return null;
	}
}
