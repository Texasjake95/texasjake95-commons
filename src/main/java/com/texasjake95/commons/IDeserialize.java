package com.texasjake95.commons;


public interface IDeserialize<T,E> {
	
	public E deserialize(T t);
}
