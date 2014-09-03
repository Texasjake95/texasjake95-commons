package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveFloat extends SaveNumber {

	private float value;

	public SaveFloat(String name)
	{
		super((byte) 9, name);
	}

	public float getValue()
	{
		return this.value;
	}

	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = new Float(this.getValue(in));
	}

	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		this.saveToOutputStream(out, this.value);
	}

	public SaveBase setValue(float value)
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
