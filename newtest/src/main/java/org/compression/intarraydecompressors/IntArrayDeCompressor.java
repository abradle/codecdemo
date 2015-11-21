package org.compression.intarraydecompressors;

import java.util.ArrayList;

public interface IntArrayDeCompressor {
	// WE DO WANT SPECIFICALLY AN ARRAYLIST HERE
	public ArrayList<Integer> decompressIntArray(ArrayList<Integer> inArray);
}
