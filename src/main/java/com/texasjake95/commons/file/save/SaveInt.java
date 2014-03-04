package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveInt extends SaveBase {
	
	private int value = 0;
	
	public SaveInt(String name)
	{
		super((byte) 1, name);
	}
	
	public SaveInt setValue(int value)
	{
		this.value = value;
		return this;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeInt(value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readInt();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
