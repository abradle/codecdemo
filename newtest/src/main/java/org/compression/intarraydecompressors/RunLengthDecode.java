package org.compression.intarraydecompressors;

import java.util.ArrayList;

public class RunLengthDecode implements IntArrayDeCompressor{
	public ArrayList<Integer> decompressIntArray(ArrayList<Integer> inArray) {
		// TODO Auto-generated method stub
    	ArrayList<Integer> outArray =  new ArrayList<Integer>();
    	// Loop through the vals
	    for (int i = 0; i < inArray.size(); i+=2) {
	    	// Get the value out here
			int intIn = inArray.get(i);
			int numOfInt = inArray.get(i+1);
			// Now add these to the array
			for (int j=0; j<numOfInt; j++){
				outArray.add(intIn);
			}
	    }
		return outArray;
	}

}
