package com.texasjake95.commons.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Lists;
import com.texasjake95.commons.helpers.Checker;

/**
 * 
 * Use this to create a array of numbers
 * 
 * @author Texasjake95
 * 
 */
public class Range implements Iterable<Integer> {
	
	/**
	 * Start is 0 Step is 1 exclusive
	 * 
	 * @param end
	 *            the number to stop before
	 * @return a list of all the integers from 0 to end - 1
	 */
	public static Range range(int end)
	{
		return range(end, false);
	}
	
	/**
	 * Start is 0 Step is 1
	 * 
	 * @param end
	 *            the number to stop before if not inclusive
	 * @param inclusive
	 *            include the end number if applicable
	 * @return a list of all the integers from 0 to end if inclusive
	 */
	public static Range range(int end, boolean inclusive)
	{
		return range(0, end, 1, inclusive);
	}
	
	/**
	 * Step is 1 exclusive
	 * 
	 * @param start
	 *            the number to start on
	 * @param end
	 *            the number to stop before
	 * @return a list of all the integers from start to end - 1
	 */
	public static Range range(int start, int end)
	{
		return range(start, end, false);
	}
	
	/**
	 * Step is 1
	 * 
	 * @param start
	 *            the number to start on
	 * @param end
	 *            the number to stop before if not inclusive
	 * @param inclusive
	 *            include the end number if applicable
	 * @return a list of all the integers from start to end - 1 skipping every
	 *         step of number
	 */
	public static Range range(int start, int end, boolean inclusive)
	{
		return range(start, end, 1, inclusive);
	}
	
	/**
	 * 
	 * @param start
	 *            the number to start on
	 * @param end
	 *            the number to stop before if applicable
	 * @param step
	 *            the step is the amount of numbers skipped until the next
	 *            number
	 * @return a list of all the integers from start to end - 1 skipping every
	 *         step of number
	 */
	public static Range range(int start, int end, int step)
	{
		return range(start, end, step, false);
	}
	
	/**
	 * 
	 * @param start
	 *            the number to start on
	 * @param end
	 *            the number to stop before if not inclusive and if applicable
	 * @param step
	 *            the step is the amount of numbers skipped until the next
	 *            number
	 * @param inclusive
	 *            include the end number if applicable
	 * @return a list of all the integers from start to end - 1 skipping every
	 *         step of number
	 */
	public static Range range(int start, int end, int step, boolean inclusive)
	{
		return new Range(start, end, step, inclusive);
	}
	
	// Class start
	private ArrayList<Integer> range = Lists.newArrayList();;
	
	private Range(int end)
	{
		this(0, end);
	}
	
	private Range(int start, int end)
	{
		this(start, end, 1);
	}
	
	private Range(int start, int end, int step)
	{
		this(start, end, step, false);
	}
	
	private Range(int start, int end, int step, boolean inclusive)
	{
		if (Checker.areAllTrue(step > 0, start < end))
			if (inclusive)
				for (int i = start; i <= end; i += step)
					range.add(i);
			else
				for (int i = start; i < end; i += step)
					range.add(i);
		else if (Checker.areAllTrue(step < 0, start > end))
			if (inclusive)
				for (int i = start; i >= end; i += step)
					range.add(i);
			else
				for (int i = start; i > end; i += step)
					range.add(i);
		else if (Checker.areAllTrue(step > 0, start > end))
			if (inclusive)
				for (int i = start; i >= end; i -= step)
					range.add(i);
			else
				for (int i = start; i > end; i -= step)
					range.add(i);
		else if (Checker.areAllTrue(step < 0, start < end))
			if (inclusive)
				for (int i = start; i <= end; i -= step)
					range.add(i);
			else
				for (int i = start; i < end; i -= step)
					range.add(i);
		else
			System.out.println("NOOP");
	}
	
	public Range reverse()
	{
		Collections.reverse(range);
		return this;
	}
	
	@Override
	public Iterator<Integer> iterator()
	{
		return range.iterator();
	}
}
