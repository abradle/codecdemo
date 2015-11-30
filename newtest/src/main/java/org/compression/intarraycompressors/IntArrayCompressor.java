package org.compression.intarraycompressors;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.compression.domstructureholders.CoreSingleStructure;

public interface IntArrayCompressor {
	// WE DO WANT SPECIFICALLY AN ARRAYLIST HERE
	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray);
}
