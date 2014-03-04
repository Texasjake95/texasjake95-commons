package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveShort extends SaveBase {
	
	public short value = 0;
	
	public SaveShort(String name)
	{
		super((byte) 6, name);
	}
	
	public SaveShort setValue(short value)
	{
		this.value = value;
		return this;
	}
	
	public short getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeShort(value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readShort();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
