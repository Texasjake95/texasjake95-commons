package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveString extends SaveBase {
	
	private String value = "";
	
	public SaveString(String name)
	{
		super((byte) 4, name);
	}
	
	public SaveString setValue(String value)
	{
		this.value = value;
		return this;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		out.writeUTF(value);
	}
	
	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = in.readUTF();
	}
	
	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
