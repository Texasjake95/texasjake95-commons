package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveLong extends SaveBase {
	
	private long value = 0L;
	
	public SaveLong(String name)
	{
		super((byte) 7, name);
	}
	
	public SaveLong setValue(long value)
	{
		this.value = value;
		return this;
	}
	
	public long getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeLong(value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readLong();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
