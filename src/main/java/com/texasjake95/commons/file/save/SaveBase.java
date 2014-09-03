package com.texasjake95.commons.file.save;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.texasjake95.commons.helpers.StreamHelper;

public abstract class SaveBase implements StreamHelper {

	public final byte id;
	public String name;

	public SaveBase(byte id, String name)
	{
		this.id = id;
		this.name = name;
	}

	protected abstract void loadFromInputStream(DataInput in) throws IOException;

	public void save(DataOutput out)
	{
		try
		{
			out.writeByte(this.id);
			out.writeUTF(this.name);
			this.saveToOutputStream(out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void saveToOutputStream(DataOutput out) throws IOException;

	public SaveBase setName(String name)
	{
		this.name = name;
		return this;
	}

	public byte[] toByteArray()
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		this.save(data);
		return bytes.toByteArray();
	}

	@Override
	public String toString()
	{
		return this.name + "-" + this.id;
	}

	public static SaveBase getSaveBase(String name, byte id)
	{
		switch (id)
		{
			case 0:
				return new SaveEnd();
			case 1:
				return new SaveInt(name);
			case 2:
				return new SaveByte(name);
			case 3:
				return new SaveBoolean(name);
			case 4:
				return new SaveString(name);
			case 5:
				return new SaveDouble(name);
			case 6:
				return new SaveShort(name);
			case 7:
				return new SaveLong(name);
			case 8:
				return new SaveCompound(name);
			case 9:
				return new SaveFloat(name);
			case 10:
				return new SaveList(name);
			default:
				return new SaveEnd();
		}
	}

	public static SaveBase load(DataInput in)
	{
		SaveBase saveBase = null;
		try
		{
			byte id = in.readByte();
			String name = in.readUTF();
			saveBase = getSaveBase(name, id);
			saveBase.loadFromInputStream(in);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (saveBase == null)
			saveBase = new SaveEnd();
		return saveBase;
	}
}
