package org.compression.biodecompression;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NoFloatDataStruct;
import org.compression.domstructureholders.NonAtomDataStruct;

public class DeCompressDoubles extends AbstractBioDeCompressor implements BioDeCompressor {

	public CoreSingleStructure deCompresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException {
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		NoFloatDataStruct noDoubleDataS = (NoFloatDataStruct) coress;
		BioDataStruct bioDataS = new BioDataStruct();
		bu.copyProperties(bioDataS, noDoubleDataS);
		// Get all the arrays we want to compress
		// Set the coordinates
		bioDataS.set_atom_site_Cartn_x(getDoubleArrayFromInts(noDoubleDataS.get_atom_site_Cartn_xInt(),1000.0));
		bioDataS.set_atom_site_Cartn_y(getDoubleArrayFromInts(noDoubleDataS.get_atom_site_Cartn_yInt(),1000.0));
		bioDataS.set_atom_site_Cartn_z(getDoubleArrayFromInts(noDoubleDataS.get_atom_site_Cartn_zInt(),1000.0));
		// Now set the temperature factors and occupancy
		bioDataS.set_atom_site_B_iso_or_equiv(getDoubleArrayFromInts(noDoubleDataS.get_atom_site_B_iso_or_equivInt(),100.0));
		bioDataS.set_atom_site_occupancy(getDoubleArrayFromInts(noDoubleDataS.get_atom_site_occupancyInt(),100.0));
		// Now assign these to the new dataStructure
		return bioDataS;
	}
	
	// Class to take an arraylist of Doubles and compres them
	private ArrayList<Double> getDoubleArrayFromInts(List<Integer> inArray, double d){
		// Initialise the out array
		ArrayList<Double> outArray = new ArrayList<Double>(inArray.size());
		for(Integer oldInt: inArray){
			Double newDouble =  (double) oldInt;
			newDouble = (newDouble / d);
			outArray.add(newDouble);
		}
		// Now return the array
		return outArray;
		
	}

}
