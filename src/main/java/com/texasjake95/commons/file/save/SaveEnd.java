package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveEnd extends SaveBase {
	
	public SaveEnd()
	{
		super((byte) 0, (String) null);
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
	}
	
	@Override
	public String toString()
	{
		return this.name + "-" + this.id;
	}
}
