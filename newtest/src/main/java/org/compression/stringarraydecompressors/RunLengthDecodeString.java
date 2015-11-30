package org.compression.stringarraydecompressors;

import java.util.ArrayList;

public class RunLengthDecodeString implements StringArrayDeCompressor {

	public ArrayList<String> deCompressStringArray(ArrayList<String> inArray) {
		ArrayList<String> outArray =  new ArrayList<String>();
		for (int i=0; i<inArray.size(); i+=2){
			String outString = inArray.get(i);
			int numString = Integer.parseInt(inArray.get(i+1));
			
			for(int j=0; j<numString; j++){
				outArray.add(outString);
			}
		}
		return outArray;
	}

}
