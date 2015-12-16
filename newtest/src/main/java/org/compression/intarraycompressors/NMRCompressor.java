package org.compression.intarraycompressors;

import java.util.ArrayList;

public class NMRCompressor extends AbstractIntArrayCompressor implements IntArrayCompressor{

	@Override
	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray) {
		// Funtion takes multiple columns and does the delta on the columns
		ArrayList<Integer> outArray = new ArrayList<Integer>(inArray.size());
		
		
		return null;
	}
	
	public ArrayList<Integer> compressIntArrayNMR(ArrayList<Integer> inArray, Integer numModels) {
		// Num Atoms
		int numAtoms = inArray.size()/numModels;
		// Funtion takes multiple columns and does the delta on the columns
		ArrayList<Integer> outArray = new ArrayList<Integer>();
		for(int j=0; j<numModels;j++){
			for(int i=0; i<numAtoms;i++){
				int index = i+j*numAtoms;
				int oldIndex = i+(j-1)*numAtoms;
				if(j==0){
					// First model store the coord (or deltas)
					outArray.add(inArray.get(index));
				}
				else{
					// Then just store deltas on these
					int newVal = inArray.get(index)-inArray.get(oldIndex);
					outArray.add(newVal);
				}
				
			}
		}
		return outArray;
	}

}
