package com.texasjake95.commons.file.save;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.Lists;

public class SaveList<E extends SaveBase> extends SaveBase {
	
	private ArrayList<E> list = Lists.newArrayList();
	
	public SaveList(String name)
	{
		super((byte) 10, name);
	}
	
	@Override
	protected void saveToOutputStream(DataOutput out) throws IOException
	{
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
			list.add((E) base);
			byte newID = in.readByte();
			if (newID != id)
				throw new IllegalArgumentException("Base types are not right");
			id = newID;
		}
	}
	
	public E getSaveBaseAt(int i)
	{
		return this.list.get(i);
	}
	
	public void removeSave(E base)
	{
		this.list.remove(base);
	}
	
	public Iterator<E> iterator()
	{
		return this.list.iterator();
	}
}
