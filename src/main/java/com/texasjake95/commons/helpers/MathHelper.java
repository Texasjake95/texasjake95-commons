package com.texasjake95.commons.helpers;

public class MathHelper {

	/**
	 *
	 * @param numToRound
	 *            The number to round
	 * @param digitPassDecimal
	 *            The number of decimal places to round to (neg means in front of the decimal
	 *            place)
	 * @return the number rounded
	 */
	public static double round(double numToRound, int digitPassDecimal)
	{
		double decimal = Math.pow(10, digitPassDecimal);
		double num = Math.round(numToRound * decimal) / decimal;
		return (float) num;
	}

	/**
	 *
	 * @param numToRound
	 *            The number to round
	 * @param digitPassDecimal
	 *            The number of decimal places to round to (neg means in front of the decimal
	 *            place)
	 * @return the number rounded
	 */
	public static float round(float numToRound, int digitPassDecimal)
	{
		return (float) round((double) numToRound, digitPassDecimal);
	}

	private MathHelper()
	{
		// NOOP
	}
}
