package com.texasjake95.commons.util.pair;

import java.io.Serializable;

public class ObjectPair<E, T> implements Serializable {
	
	private static final long serialVersionUID = 4367677487339615210L;
	private E e;
	private T t;
	
	public ObjectPair(E e, T t)
	{
		this.e = e;
		this.t = t;
	}
	
	protected E getObject1()
	{
		return e;
	}
	
	protected T getObject2()
	{
		return t;
	}
	
	protected void setObject1(E e)
	{
		this.e = e;
	}
	
	protected void setObject2(T t)
	{
		this.t = t;
	}
}
