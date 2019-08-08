package games.bevs.core.commons.utils;

public class MathUtils 
{
	public static double round(double value, int dp)
	{
		double offset = Math.pow(10, dp);
		return Math.round(value * offset) / offset;
	}
}
