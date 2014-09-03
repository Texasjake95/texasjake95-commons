package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SaveShort extends SaveNumber {

	public short value = 0;

	public SaveShort(String name)
	{
		super((byte) 6, name);
	}

	public short getValue()
	{
		return this.value;
	}

	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		this.value = new Short(this.getValue(in));
	}

	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		this.saveToOutputStream(out, this.value);
	}

	public SaveShort setValue(short value)
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
