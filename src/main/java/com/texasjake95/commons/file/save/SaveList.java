package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.Lists;

public class SaveList extends SaveBase {
	
	private ArrayList<SaveBase> list = Lists.newArrayList();
	
	public SaveList(String name)
	{
		super((byte) 10, name);
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
		Iterator<SaveBase> iterator = this.iterator();
		while (iterator.hasNext())
		{
			SaveBase base = iterator.next();
			base.save(out);
		}
		out.writeByte(0);
	}
	
	@SuppressWarnings("unchecked")
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
			// System.out.println("Loading:" + base + " for " + this.name);
			list.add(base);
			byte newID = in.readByte();
			if (newID != id)
				throw new IllegalArgumentException("Base types are not the same");
			id = newID;
		}
	}
	
	public <T extends SaveBase> T getSaveBaseAt(int i)
	{
		return (T) this.list.get(i);
	}
	
	public void addSave(SaveBase base)
	{
		this.list.add(base);
	}
	
	public void removeSave(SaveBase base)
	{
		this.list.remove(base);
	}
	
	public Iterator<SaveBase> iterator()
	{
		return this.list.iterator();
	}
	
	public int length()
	{
		return this.list.size();
	}
}
