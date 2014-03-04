package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveFloat extends SaveBase {
	
	private float value;
	
	public SaveFloat(String name)
	{
		super((byte) 9, name);
	}
	
	public SaveBase setValue(float value)
	{
		this.value = value;
		return this;
	}
	
	public float getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeFloat(this.value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readFloat();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
