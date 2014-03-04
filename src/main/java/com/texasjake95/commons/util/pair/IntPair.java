package com.texasjake95.commons.util.pair;

public class IntPair extends ComparableSameTypePair<Integer> {
	
	public IntPair(int int1, int int2)
	{
		super(int1, int2);
	}
	
	public void setIntX(int x)
	{
		this.setObject1(x);
	}
	
	public void setIntY(int y)
	{
		this.setObject2(y);
	}
	
	public int getIntX()
	{
		return this.getObject1();
	}
	
	public int getIntY()
	{
		return this.getObject2();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof IntPair))
		{
			return false;
		}
		IntPair intPair = (IntPair) obj;
		return this.compareTo(intPair) == 0;
	}
	
	private static final long serialVersionUID = -5044528796135360263L;
}