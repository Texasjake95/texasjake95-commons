package com.texasjake95.commons.helpers;

public class MathHelper {
	
	private MathHelper()
	{
		//NOOP
	}
	
	/**
	 * 
	 * @param numToRound
	 *            The number to round
	 * @param digitPassDecimal
	 *            The number of decimal places to round to (neg means in front
	 *            of the decimal place)
	 * @return the number rounded
	 */
	public static float round(float numToRound, int digitPassDecimal)
	{
		return (float) round((double) numToRound, digitPassDecimal);
	}
	
	/**
	 * 
	 * @param numToRound
	 *            The number to round
	 * @param digitPassDecimal
	 *            The number of decimal places to round to (neg means in front
	 *            of the decimal place)
	 * @return the number rounded
	 */
	public static double round(double numToRound, int digitPassDecimal)
	{
		double decimal = Math.pow(10, digitPassDecimal);
		System.out.println("Decimal= " + decimal);
		double num = Math.round(numToRound * decimal) / decimal;
		System.out.println("Round= " + numToRound);
		System.out.println("new Number= " + num);
		return (float) num;
	}
}
