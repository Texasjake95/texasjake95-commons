package com.texasjake95.commons.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOutput {
	
	FileWriter fw;
	PrintWriter pw;
	boolean isValid = true;
	
	public FileOutput(File file)
	{
		try
		{
			fw = new FileWriter(file);
			pw = new PrintWriter(fw);
		}
		catch (IOException e)
		{
			isValid = false;
		}
	}
	
	public void println(String string)
	{
		if (isValid)
			this.pw.println(string);
	}
	
	public void close() throws IOException
	{
		if (isValid)
		{
			this.pw.close();
			this.fw.close();
		}
	}
}
