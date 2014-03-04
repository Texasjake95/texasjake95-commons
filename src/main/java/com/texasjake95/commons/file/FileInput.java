package com.texasjake95.commons.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

public class FileInput {
	
	FileReader fr;
	BufferedReader br;
	boolean isValid = true;
	
	public FileInput(File file)
	{
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
		}
		catch (FileNotFoundException e)
		{
			isValid = false;
		}
	}
	
	public String readLine() throws IOException
	{
		if (isValid)
			return this.br.readLine();
		return "";
	}
	
	public ArrayList<String> getFileLines()
	{
		if (!isValid)
			return new ArrayList<String>();
		ArrayList<String> lines = Lists.newArrayList();
		String line;
		try
		{
			line = this.readLine();
			while (line != null)
			{
				lines.add(line);
				line = this.readLine();
			}
		}
		catch (IOException e)
		{
		}
		return lines;
	}
	
	public void close() throws IOException
	{
		if (isValid)
		{
			this.br.close();
			this.fr.close();
		}
	}
}
