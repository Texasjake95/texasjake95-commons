package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveBoolean extends SaveBase {
	
	private boolean value = false;
	
	public SaveBoolean(String name)
	{
		super((byte) 3, name);
	}
	
	public SaveBoolean setValue(boolean value)
	{
		this.value = value;
		return this;
	}
	
	public boolean getValue()
	{
		return this.value;
	}
	
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeByte(value ? 1 : 0);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		byte b = in.readByte();
		this.value = b == 1;
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
