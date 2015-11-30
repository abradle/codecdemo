package org.compression.biodecompression;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NoFloatDataStruct;
import org.compression.domstructureholders.NonAtomDataStruct;
import org.compression.domstructureholders.ResidueGraphDataStruct;

public class DeCompressResidues extends AbstractBioDeCompressor implements BioDeCompressor {

	public CoreSingleStructure deCompresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException {
		// Set up the input structure
		ResidueGraphDataStruct resGraphDS = (ResidueGraphDataStruct) coress;
		// Set up the output structure
		NoFloatDataStruct outStruct = new NoFloatDataStruct();
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		// Copy over shared properties
		bu.copyProperties(outStruct, resGraphDS);
		// Get the set as an array
		List<String> infoArray = new ArrayList<String>(resGraphDS.getResToInfo());
		// Now fill the unshared properties
		// Loop over the
		for (int index: resGraphDS.getResOrder()){
			updateStruct(index, outStruct, infoArray);
		}
		// The out object
		return outStruct;
	}

	private void updateStruct(int index, NoFloatDataStruct outStruct, List<String> infoArray) {
		// Get the information
		String myInfo = infoArray.get(index);
		String[] mySplitInfo = myInfo.split("\n");
		String[] topLine = mySplitInfo[0].split("\t");
		String groupPDB = topLine[0];
		String compID = topLine[1];
		String authCompId = topLine[2];
		int counter = 0;
		for (String line: mySplitInfo){
			if(counter==0){
				counter+=1;
				continue;
			}
			outStruct.get_atom_site_group_PDB().add(groupPDB);
			outStruct.get_atom_site_label_comp_id().add(compID);
			outStruct.get_atom_site_auth_comp_id().add(authCompId);
			String[] thisLine = line.split("\t");
			// Get these on each line
			outStruct.get_atom_site_auth_atom_id().add(thisLine[0]);
			outStruct.get_atom_site_label_atom_id().add(thisLine[1]);
			outStruct.get_atom_site_symbol().add(thisLine[2]);

		}

	}

}
