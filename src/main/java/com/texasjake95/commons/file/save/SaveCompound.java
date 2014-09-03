package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.Maps;

import com.texasjake95.commons.file.SaveFileInputStream;
import com.texasjake95.commons.file.SaveFileOutputStream;
import com.texasjake95.commons.file.TXFile;

public class SaveCompound extends SaveBase {

	private HashMap<String, SaveBase> dataMap = Maps.newHashMap();

	public SaveCompound()
	{
		this("");
	}

	public SaveCompound(String name)
	{
		super((byte) 8, name);
	}

	public void addData(SaveBase base)
	{
		this.dataMap.put(base.name, base);
	}

	// boolean
	public boolean getBoolean(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveBoolean))
			this.setBoolean(name, false);
		return ((SaveBoolean) this.dataMap.get(name)).getValue();
	}

	// byte
	public byte getByte(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveByte))
			this.setByte(name, (byte) 0);
		return ((SaveByte) this.dataMap.get(name)).getValue();
	}

	// compound
	public SaveCompound getCompound(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveCompound))
			this.setCompound(name, new SaveCompound(name));
		return (SaveCompound) this.dataMap.get(name);
	}

	public SaveBase getData(String string)
	{
		return this.dataMap.get(string);
	}

	// double
	public double getDouble(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveDouble))
			this.setDouble(name, 0.0D);
		return ((SaveDouble) this.dataMap.get(name)).getValue();
	}

	// float
	public float getFloat(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveFloat))
			this.setFloat(name, 0.0F);
		return ((SaveFloat) this.dataMap.get(name)).getValue();
	}

	// int
	public int getInt(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveInt))
			this.setInt(name, 0);
		return ((SaveInt) this.dataMap.get(name)).getValue();
	}

	// list
	public SaveList getList(String name, int id)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveList) || ((SaveList) this.dataMap.get(name)).length() > 0 && ((SaveList) this.dataMap.get(name)).getSaveBaseAt(0).id != id)
			this.setList(name, new SaveList(name));
		return (SaveList) this.dataMap.get(name);
	}

	// long
	public long getLong(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveLong))
			this.setLong(name, 0L);
		return ((SaveLong) this.dataMap.get(name)).getValue();
	}

	// short
	public short getShort(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveShort))
			this.setShort(name, (short) 0);
		return ((SaveShort) this.dataMap.get(name)).getValue();
	}

	// String
	public String getString(String name)
	{
		if (!this.hasData(name) || !(this.dataMap.get(name) instanceof SaveString))
			this.setString(name, "");
		return ((SaveString) this.dataMap.get(name)).getValue();
	}

	public boolean hasData(String name)
	{
		return this.dataMap.containsKey(name);
	}

	@Override
	protected void loadFromInputStream(DataInput in) throws IOException
	{
		byte id = in.readByte();
		while (id > 0)
		{
			String name = in.readUTF();
			SaveBase base = getSaveBase(name, id);
			if (base instanceof SaveEnd)
				break;
			base.loadFromInputStream(in);
			System.out.println("Loading:" + base + " for " + this.name);
			this.addData(base);
			id = in.readByte();
		}
	}

	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		Iterator<SaveBase> iterator = this.dataMap.values().iterator();
		while (iterator.hasNext())
		{
			SaveBase saveBase = iterator.next();
			saveBase.save(out);
		}
		out.writeByte(0);
	}

	public void setBoolean(String name, boolean value)
	{
		this.addData(new SaveBoolean(name).setValue(value));
	}

	public void setByte(String name, byte value)
	{
		this.addData(new SaveByte(name).setValue(value));
	}

	public void setCompound(String name, SaveCompound value)
	{
		value.name = name;
		this.addData(value);
	}

	public void setDouble(String name, double value)
	{
		this.addData(new SaveDouble(name).setValue(value));
	}

	public void setFloat(String name, float value)
	{
		this.addData(new SaveFloat(name).setValue(value));
	}

	public void setInt(String name, int value)
	{
		this.addData(new SaveInt(name).setValue(value));
	}

	public void setList(String name, SaveList value)
	{
		this.addData(value.setName(name));
	}

	public void setLong(String name, long value)
	{
		this.addData(new SaveLong(name).setValue(value));
	}

	public void setShort(String name, short value)
	{
		this.addData(new SaveShort(name).setValue(value));
	}

	public void setString(String name, String value)
	{
		this.addData(new SaveString(name).setValue(value));
	}

	public static SaveCompound getSaveCompoundFromFile(File saveFile)
	{
		SaveCompound saveCompound;
		try
		{
			if (saveFile.exists())
			{
				SaveFileInputStream load = new SaveFileInputStream(new FileInputStream(saveFile));
				saveCompound = (SaveCompound) SaveBase.load(load);
				load.close();
			}
			else
			{
				saveFile.createNewFile();
				saveCompound = new SaveCompound();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			saveCompound = new SaveCompound();
		}
		return saveCompound;
	}

	public static SaveCompound getSaveCompoundFromFile(String saveFileName)
	{
		File temp = new File(saveFileName);
		TXFile saveFile = new TXFile(temp);
		return getSaveCompoundFromFile(saveFile);
	}

	public static void saveCompoundToFile(File saveFile, SaveCompound saveCompound)
	{
		try
		{
			SaveFileOutputStream save = new SaveFileOutputStream(new FileOutputStream(saveFile));
			saveCompound.save(save);
			save.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void saveCompoundToFile(String saveFileName, SaveCompound saveCompound)
	{
		File temp = new File(saveFileName);
		TXFile saveFile = new TXFile(temp);
		saveCompoundToFile(saveFile, saveCompound);
	}
}
