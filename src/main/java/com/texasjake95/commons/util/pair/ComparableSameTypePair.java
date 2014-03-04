package com.texasjake95.commons.util.pair;

public class ComparableSameTypePair<T extends Comparable<T>> extends SameTypePair<T> implements Comparable<ComparableSameTypePair<T>> {
	
	private static final long serialVersionUID = -5800884668917991700L;
	
	public ComparableSameTypePair(T first, T second)
	{
		super(first, second);
	}
	
	@Override
	public int compareTo(ComparableSameTypePair<T> o)
	{
		if (this.getObject1().compareTo(o.getObject1()) == 0)
		{
			return this.getObject2().compareTo(o.getObject2());
		}
		return this.getObject1().compareTo(o.getObject1());
	}
}
