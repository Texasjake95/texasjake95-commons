package com.texasjake95.commons.util;

import java.util.Collections;

public class SortableCustomList<T extends Comparable<? super T>> extends CustomArrayList<T> {
	
	public SortableCustomList<T> sort()
	{
		this.doSort();
		return this;
	}
	
	public void doSort()
	{
		Collections.sort(this.list);
	}
}
