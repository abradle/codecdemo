package org.compression.intarraydecompressors;

import java.util.ArrayList;
import java.util.Collections;

public class AbstractIntArrayDeCompressor {
	public static int[] convertIntegers(ArrayList<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
	public static ArrayList<Integer> convertArrayListIntegers(int[] ints)
	{
		ArrayList<Integer> integers = new ArrayList<Integer>();
	    for (int i=0; i < ints.length; i++)
	    {
	        integers.add(ints[i]);
	    }
	    return integers;
	}	
	private static int minIndex (ArrayList<Integer> new_array) {
		  return new_array.indexOf (Collections.min(new_array)); }

	public int minVal (ArrayList<Integer> new_array) {
		return new_array.get(minIndex(new_array)); }
}
