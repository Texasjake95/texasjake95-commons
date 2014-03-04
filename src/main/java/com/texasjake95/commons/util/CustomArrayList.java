package com.texasjake95.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CustomArrayList<T> implements Iterable<T>, Cloneable {
	
	public final ArrayList<T> list = new ArrayList<T>();
	
	public CustomArrayList()
	{
	}
	
	public CustomArrayList<T> add(T object)
	{
		this.list.add(object);
		return this;
	}
	
	public CustomArrayList<T> addAll(T[] objects)
	{
		for (T object : objects)
			this.list.add(object);
		return this;
	}
	
	public CustomArrayList<T> addAll(Collection<? extends T> c)
	{
		this.list.addAll(c);
		return this;
	}
	
	public CustomArrayList<T> addAll(int index, Collection<? extends T> c)
	{
		this.list.addAll(index, c);
		return this;
	}
	
	public CustomArrayList<T> remove(T object)
	{
		this.list.remove(object);
		return this;
	}
	
	public CustomArrayList<T> removeList(CustomArrayList<T> listToRemove)
	{
		for (T object : listToRemove.list)
			this.list.remove(object);
		return this;
	}
	
	public CustomArrayList<T> mergeLists(CustomArrayList<T> listToMerge)
	{
		for (T object : listToMerge.list)
			this.list.add(object);
		return this;
	}
	
	public ArrayList<T> getList()
	{
		return this.list;
	}
	
	public boolean contains(T object)
	{
		return this.list.contains(object);
	}
	
	public CustomArrayList<T> cleanUp(T[] temp)
	{
		this.doCleanUp(temp);
		return this;
	}
	
	public void doCleanUp(T[] temp)
	{
		for (T object : this.toArray(temp))
		{
			while (this.list.contains(object))
			{
				this.list.remove(object);
			}
			this.list.add(object);
		}
	}
	
	public T[] toArray(T[] tempArray)
	{
		return this.list.toArray(tempArray);
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return this.list.iterator();
	}
}
