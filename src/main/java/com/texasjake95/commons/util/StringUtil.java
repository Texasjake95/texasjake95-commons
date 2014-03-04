package com.texasjake95.commons.util;

import java.util.Random;

public class StringUtil {
	
	private static final String dCase = "abcdefghijklmnopqrstuvwxyz";
	private static final String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String intChar = "0123456789";
	private static Random r = new Random();
	
	public static String createABCString(int length)
	{
		String tempuCase = uCase;
		StringBuilder string = new StringBuilder();
		int spot = 0;
		while (string.length() != length)
		{
			spot = r.nextInt(tempuCase.length());
			string.append(tempuCase.charAt(spot));
			tempuCase = tempuCase.replaceAll(tempuCase.charAt(spot) + "", "");
			spot = 0;
		}
		return string.toString();
	}
	
	public static String createabcString(int length)
	{
		String tempdCase = dCase;
		StringBuilder string = new StringBuilder();
		int spot = 0;
		while (string.length() != length)
		{
			spot = r.nextInt(tempdCase.length());
			string.append(tempdCase.charAt(spot));
			tempdCase = tempdCase.replaceAll(tempdCase.charAt(spot) + "", "");
			spot = 0;
		}
		return string.toString();
	}
	
	public static String create123String(int length)
	{
		if (length > intChar.length())
		{
			length = intChar.length();
		}
		String tempintChar = intChar;
		StringBuilder string = new StringBuilder();
		int spot = 0;
		while (string.length() != length)
		{
			spot = r.nextInt(tempintChar.length());
			string.append(tempintChar.charAt(spot));
			tempintChar = tempintChar.replaceAll(tempintChar.charAt(spot) + "", "");
			spot = 0;
		}
		return string.toString();
	}
	
	public static String createRandomString(int length)
	{
		String tempdCase = dCase;
		String tempuCase = uCase;
		String tempintChar = intChar;
		StringBuilder string = new StringBuilder();
		int spot = 0;
		int pick;
		while (string.length() != length)
		{
			pick = r.nextInt(4);
			switch (pick)
			{
				case 1:
					spot = r.nextInt(tempdCase.length());
					string.append(tempdCase.charAt(spot));
					tempdCase = tempdCase.replaceAll(tempdCase.charAt(spot) + "", "");
					break;
				case 2:
					spot = r.nextInt(tempuCase.length());
					string.append(tempuCase.charAt(spot));
					tempuCase = tempuCase.replaceAll(tempuCase.charAt(spot) + "", "");
					break;
				default:
					spot = r.nextInt(tempintChar.length());
					string.append(tempintChar.charAt(spot));
					tempintChar = tempintChar.replaceAll(tempintChar.charAt(spot) + "", "");
					break;
			}
			spot = 0;
		}
		return string.toString();
	}
}
