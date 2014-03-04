package com.texasjake95.commons.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class TXFile extends File {
	
	private static final long serialVersionUID = 4678674199045751875L;
	
	public TXFile(URI uri)
	{
		super(uri);
	}
	
	public TXFile(File file)
	{
		super(file.getAbsolutePath());
	}
	
	public TXFile(File file, String string)
	{
		super(file, string);
	}
	
	public TXFile(String string)
	{
		super(string);
	}
	
	public TXFile(String string, String string2)
	{
		super(string, string2);
	}
	
	@Override
	public boolean createNewFile() throws IOException
	{
		if (!this.getParentFile().exists())
			this.mkdirs();
		return super.createNewFile();
	}
	
	@Override
	public boolean mkdirs()
	{
		return this.getParentFile().mkdirs();
	}
	
	public String copyFileTo(String string)
	{
		return this.copyFileTo(new File(string));
	}
	
	public String copyFileTo(File file)
	{
		return FileHelper.copyFileTo(this.getAbsolutePath(), file.getAbsolutePath());
	}
}
