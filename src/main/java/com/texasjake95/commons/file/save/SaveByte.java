package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveByte extends SaveBase {
	
	private byte value = 0;
	
	public SaveByte(String name)
	{
		super((byte) 2, name);
	}
	
	public SaveByte setValue(byte value)
	{
		this.value = value;
		return this;
	}
	
	public byte getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeByte(value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readByte();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
