package org.compression.intarraydecompressors;

import java.util.ArrayList;

public class RemoveDeltas extends AbstractIntArrayDeCompressor implements IntArrayDeCompressor {

	public ArrayList<Integer> decompressIntArray(ArrayList<Integer> inArray) {
    	// Function to remove the Delta encoding from an array
		ArrayList<Integer> outArray = new ArrayList<Integer>();
		int old_int = 0;
	    for (int i = 0; i < inArray.size(); i++) {
	    	// Do the magic here
			int num_int = inArray.get(i);
			if (i==0){
				old_int = num_int;
				outArray.add(num_int);
			}
			else{
				int this_int = num_int + old_int;
				outArray.add((int) this_int);
				old_int = this_int;
			}
	    }
	    return outArray;
	}

}
