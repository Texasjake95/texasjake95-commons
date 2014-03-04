package com.texasjake95.commons.util.pair;

public class SameTypePair<T> extends ObjectPair<T, T> {
	
	private static final long serialVersionUID = -3470229426651836541L;
	
	public SameTypePair(T first, T second)
	{
		super(first, second);
	}
}
