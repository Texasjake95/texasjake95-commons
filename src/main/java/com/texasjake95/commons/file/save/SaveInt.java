package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveInt extends SaveNumber {

	private int value = 0;

	public SaveInt(String name)
	{
		super((byte) 1, name);
	}

	public int getValue()
	{
		return this.value;
	}

	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = new Integer(super.getValue(in));
	}

	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		this.saveToOutputStream(out, this.value);
	}

	public SaveInt setValue(int value)
	{
		this.value = value;
		return this;
	}

	@Override
	public String toString()
	{
		return this.name + ":" + this.value + "-" + this.id;
	}
}
