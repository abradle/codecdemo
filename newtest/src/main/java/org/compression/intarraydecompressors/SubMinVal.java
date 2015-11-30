package org.compression.intarraydecompressors;

import java.util.ArrayList;


public class SubMinVal extends AbstractIntArrayDeCompressor implements IntArrayDeCompressor {

	public ArrayList<Integer> decompressIntArray(ArrayList<Integer> inArray) {
		// Loop over the array
		ArrayList<Integer> outArray = new ArrayList<Integer>(inArray.size()-1);
		int minVal = inArray.get(0);
		for(int i=1; i<inArray.size();i++){
			outArray.add(inArray.get(i)-minVal);
		}
		return outArray;
	}

}
