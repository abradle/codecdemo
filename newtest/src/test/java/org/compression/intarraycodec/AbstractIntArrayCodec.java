package org.compression.intarraycodec;

import java.util.ArrayList;
import java.util.Random;

import org.compression.intarraycompressors.IntArrayCompressor;
import org.compression.intarraydecompressors.IntArrayDeCompressor;


public class AbstractIntArrayCodec {
	public boolean runTest(IntArrayCompressor intArrComp, IntArrayDeCompressor intArrDeComp){
		ArrayList<Integer> inArray = getIntArray(100);
		ArrayList<Integer> compressedArray = intArrComp.compressIntArray(inArray);
		ArrayList<Integer> outArray = intArrDeComp.decompressIntArray(compressedArray);
		// Now do the test
		if(inArray.size()!=outArray.size()){			
			return false;
		}
		for(int i=0; i<inArray.size(); i++){
			if(inArray.get(i).equals(outArray.get(i))==false){
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<Integer> getIntArray(Integer arraySize){
		Random rg = new Random();
		ArrayList<Integer> newArray = new ArrayList<Integer>(arraySize);
		for (int i=0;i<arraySize;i++){
			newArray.add(rg.nextInt(arraySize));
		}
		return newArray;
		
	}
	
}
