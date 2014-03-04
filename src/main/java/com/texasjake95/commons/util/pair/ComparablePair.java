package com.texasjake95.commons.util.pair;

public class ComparablePair<E extends Comparable<E>, T extends Comparable<T>> extends ObjectPair<E, T> implements Comparable<ComparablePair<E, T>> {
	
	private static final long serialVersionUID = 1432535479179982164L;
	
	public ComparablePair(E e, T t)
	{
		super(e, t);
	}
	
	@Override
	public int compareTo(ComparablePair<E, T> o)
	{
		if (this.getObject1().compareTo(o.getObject1()) == 0)
		{
			return this.getObject2().compareTo(o.getObject2());
		}
		return this.getObject1().compareTo(o.getObject1());
	}
}
