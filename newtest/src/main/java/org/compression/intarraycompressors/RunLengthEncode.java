package org.compression.intarraycompressors;

import java.util.ArrayList;

public class RunLengthEncode extends AbstractIntArrayCompressor implements IntArrayCompressor  {

	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray) {
		// TODO Auto-generated method stub
    	ArrayList<Integer> outArray =  new ArrayList<Integer>();
    	int oldVal = -1;
    	int counter = 0;
    	// Loop through the vals
	    for (int i = 0; i < inArray.size(); i++) {
	    	// Get the value out here
			int num_int = inArray.get(i);
			if (num_int!=oldVal){
				if(oldVal!=-1){
					// Add the counter to the array
					outArray.add(counter);
				}
				// If it's a new number add it to the array
				outArray.add(num_int);
				counter=1;
				oldVal=num_int;
			}
			else{
				counter+=1;
			}
	    }
	    outArray.add(counter);
		return outArray;
	}

}
