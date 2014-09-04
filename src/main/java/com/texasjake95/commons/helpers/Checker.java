package com.texasjake95.commons.helpers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;

public class Checker {

	public static boolean areAllFalse(Boolean... booleans)
	{
		return !areAllTrue(Sets.newHashSet(booleans));
	}

	public static boolean areAllFalse(Iterable<Boolean> booleans)
	{
		return !areAllTrue(Sets.newHashSet(booleans));
	}

	public static boolean areAllFalse(Iterator<Boolean> booleans)
	{
		return !areAllTrue(Sets.newHashSet(booleans));
	}

	public static boolean areAllNotNull(Iterable<Object> objects)
	{
		return areAllNotNull(Sets.newHashSet(objects));
	}

	public static boolean areAllNotNull(Iterator<Object> objects)
	{
		return areAllNotNull(Sets.newHashSet(objects));
	}

	public static boolean areAllNotNull(Object... objects)
	{
		return areAllNotNull(Sets.newHashSet(objects));
	}

	private static boolean areAllNotNull(Set<Object> objectSet)
	{
		HashSet<Object> set = Sets.newHashSet(objectSet);
		return !set.contains(null);
	}

	public static boolean areAllTrue(Boolean... booleans)
	{
		return areAllTrue(Sets.newHashSet(booleans));
	}

	public static boolean areAllTrue(Iterable<Boolean> booleans)
	{
		return areAllTrue(Sets.newHashSet(booleans));
	}

	public static boolean areAllTrue(Iterator<Boolean> booleans)
	{
		return areAllTrue(Sets.newHashSet(booleans));
	}

	private static boolean areAllTrue(Set<Boolean> boolSet)
	{
		HashSet<Boolean> set = Sets.newHashSet(boolSet);
		return !set.contains(false);
	}

	public static boolean areAnyNull(Iterable<Object> objects)
	{
		return areAnyNull(Sets.newHashSet(objects));
	}

	public static boolean areAnyNull(Iterator<Object> objects)
	{
		return areAnyNull(Sets.newHashSet(objects));
	}

	public static boolean areAnyNull(Object... objects)
	{
		return areAnyNull(Sets.newHashSet(objects));
	}

	private static boolean areAnyNull(Set<Object> objectSet)
	{
		HashSet<Object> set = Sets.newHashSet(objectSet);
		return set.contains(null);
	}

	public static boolean areAnyTrue(Boolean... booleans)
	{
		HashSet<Boolean> set = Sets.newHashSet(booleans);
		return set.contains(true);
	}

	public static boolean areAnyTrue(Iterable<Boolean> booleans)
	{
		return areAnyTrue(Sets.newHashSet(booleans));
	}

	public static boolean areAnyTrue(Iterator<Boolean> booleans)
	{
		return areAnyTrue(Sets.newHashSet(booleans));
	}

	private static boolean areAnyTrue(Set<Boolean> booleans)
	{
		HashSet<Boolean> set = Sets.newHashSet(booleans);
		return set.contains(true);
	}

	public static boolean doAnyMatch(Object target, Iterable<Object> objects)
	{
		return doAnyMatch(target, Sets.newHashSet(objects));
	}

	public static boolean doAnyMatch(Object target, Iterator<Object> objects)
	{
		return doAnyMatch(target, Sets.newHashSet(objects));
	}

	public static boolean doAnyMatch(Object target, Object... objects)
	{
		return doAnyMatch(target, Sets.newHashSet(objects));
	}

	private static boolean doAnyMatch(Object target, Set<Object> set)
	{
		HashSet<Object> temp = Sets.newHashSet(set);
		return temp.contains(target);
	}

	public static boolean isNotNull(Object object)
	{
		return object != null;
	}

	public static boolean isNull(Object object)
	{
		return object == null;
	}
}
