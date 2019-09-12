package io;
//where the time input is taken for setting frame limit
public class Timer 
{
	public static double getTime()
	{
		return (double)System.nanoTime() / (double)1000000000L;
	}
}
