package org.compression.biocompressor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NonAtomDataStruct;
import org.compression.domstructureholders.OrderedDataStruct;
import org.compression.domstructureholders.ResidueGraphDataStruct;

public class AbstractBioCompressor {
	public void updateInfo(ResidueGraphDataStruct resGraphDS, CoreSingleStructure coress, NonAtomDataStruct noatomDS) throws IllegalAccessException, InvocationTargetException {
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		// Copy over shared properties
		bu.copyProperties(resGraphDS, coress);
		int oldNum = -1;
		String resName = "";
		String infoValue = "";
		// Now loop over and create the hashmaps
		for(int i=0; i<coress.findNumAtoms(); i++){
			resName = noatomDS.get_atom_site_label_comp_id().get(i);
			int resNum = noatomDS.get_atom_site_auth_seq_id().get(i);
			if(resNum!=oldNum){
				if(oldNum != -1){
					// Update the information
					updateHash(resGraphDS, resName, infoValue);
				}
				// Set it to zero
				infoValue="";
				// First line is info shared across the residue
				infoValue+=allInfo(noatomDS,i);
				oldNum=resNum;
			}
			// Function to get the required information - for each line
			infoValue+=packInfo(noatomDS, i);

		}
		// Now do the last one\
		updateHash(resGraphDS, resName, infoValue);
		// The out object
		
	}
	
	public void updateInfo(OrderedDataStruct resGraphDS, CoreSingleStructure coress, NonAtomDataStruct noatomDS) throws IllegalAccessException, InvocationTargetException {
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		// Copy over shared properties
		bu.copyProperties(resGraphDS, coress);
		int oldNum = -1;
		String resName = "";
		String infoValue = "";
		// Now loop over and create the hashmaps
		for(int i=0; i<coress.findNumAtoms(); i++){
			resName = noatomDS.get_atom_site_label_comp_id().get(i);
			int resNum = noatomDS.get_atom_site_auth_seq_id().get(i);
			if(resNum!=oldNum){
				if(oldNum != -1){
					// Update the information
					updateHash(resGraphDS, resName, infoValue);
				}
				// Set it to zero
				infoValue="";
				// First line is info shared across the residue
				infoValue+=allInfo(noatomDS,i);
				oldNum=resNum;
			}
			// Function to get the required information - for each line
			infoValue+=packInfo(noatomDS, i);

		}
		// Now do the last one\
		updateHash(resGraphDS, resName, infoValue);
		// The out object
		
	}


private void updateHash(ResidueGraphDataStruct resGraphDS, String resName, String infoValue) {
	// First check if it exists and add it if not
	resGraphDS.getResToInfo().add(infoValue);
	List<String> nameList = new ArrayList<String>(resGraphDS.getResToInfo());
	resGraphDS.getResOrder().add(nameList.indexOf(infoValue));
}

private void updateHash(OrderedDataStruct resGraphDS, String resName, String infoValue) {
	// First check if it exists and add it if not
	resGraphDS.getResToInfo().add(infoValue);
	List<String> nameList = new ArrayList<String>(resGraphDS.getResToInfo());
	resGraphDS.getResOrder().add(nameList.indexOf(infoValue));
}

private String allInfo(NonAtomDataStruct noatomDS, int i) {
	// Delimiter  - to split individual components
	String delimeter = "\t";
	// Ender - to end a record
	String ender = "\n";
	// TODO Auto-generated method stub
	String outString = noatomDS.get_atom_site_group_PDB().get(i)+delimeter;
	outString+= noatomDS.get_atom_site_label_comp_id().get(i)+ender;
//	outString+= noatomDS.get_atom_site_auth_comp_id().get(i)+ender;
	return outString;
}

private String packInfo(NonAtomDataStruct noatomDS,int i) {
	// Delimiter  - to split individual components
	String delimeter = "\t";
	// Ender - to end a record
	String ender = "\n";
	// Function to pack up the required info - has a corollary to unpack data
	/// STORE THIS INFORMATION
	String outString = "";
//	outString+=noatomDS.get_atom_site_auth_atom_id().get(i)+delimeter;
	outString+=noatomDS.get_atom_site_label_atom_id().get(i)+delimeter;
	outString+=noatomDS.get_atom_site_symbol().get(i)+ender;
	return outString;
}
}
