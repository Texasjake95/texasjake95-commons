package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveDouble extends SaveBase {
	
	private double value = 0.0D;
	
	public SaveDouble(String name)
	{
		super((byte) 5, name);
	}
	
	public SaveDouble setValue(double value)
	{
		this.value = value;
		return this;
	}
	
	public double getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeDouble(value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readDouble();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
