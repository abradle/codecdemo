package org.compression.intarraycompressors;

import java.util.ArrayList;

import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NoFloatDataStructBean;

public class AddMinVal extends AbstractIntArrayCompressor implements IntArrayCompressor {


	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray) {
		// TODO Auto-generated method stub
		// Function to remove integer values
		ArrayList<Integer> new_list = new  ArrayList<Integer>();
		int minDub = minVal(inArray);
		if(minDub<0){
			minDub=-minDub;
		}
		else{
			minDub=0;
		}
		new_list.add(minDub);
		
		for (Integer d: inArray){
		new_list.add(d+minDub);
		 
		}
		
		return new_list;
	}

}
