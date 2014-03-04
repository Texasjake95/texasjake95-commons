package com.texasjake95.commons.helpers;

public class Checker {
	
	public static boolean areAllTrue(boolean... booleans)
	{
		for (boolean bool : booleans)
			if (!bool)
				return false;
		return true;
	}
	
	public static boolean areAnyTrue(boolean... booleans)
	{
		for (boolean bool : booleans)
			if (bool)
				return true;
		return false;
	}
	
	public static boolean areAllFalse(boolean... booleans)
	{
		for (boolean bool : booleans)
			if (bool)
				return false;
		return true;
	}
	
	public static boolean doAnyMatch(Object target, Object... objects)
	{
		for(Object object : objects)
		{
			if(object == target)
				return true;
		}
		return true;
	}
	
	public static boolean isNotNull(Object object)
	{
		return object != null;
	}
	
	public static boolean isNull(Object object)
	{
		return object == null;
	}
	
	public static boolean areAllNotNull(Object... objects)
	{
		for (Object object : objects)
			if (isNull(object))
				return false;
		return true;
	}
}
