package org.compression.biocompressor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NoFloatDataStruct;
import org.compression.domstructureholders.PDBGroup;

public class CompressDists extends AbstractBioCompressor implements BioCompressor {
	/// CLASS TO TAKE -> XYZ and RETURN XYDIST OR XYDIST SINGLE VECTOR...

	@Override
	public CoreSingleStructure compresStructure(CoreSingleStructure coress)
			throws IllegalAccessException, InvocationTargetException {
		
		
		////// ERRROR HERE WITH DEALING WITH LIGANDS AND I BELIEVE MULTI CHAIN
		// Now condense the appropriate arrays	
		NoFloatDataStruct nfds = (NoFloatDataStruct) coress;
		Map<Integer, PDBGroup> groupMap = nfds.getGroupMap();
		List<List<Integer>> chainList = nfds.getGroupList();
		// Now go through and replace these with the deltas of the dists

		List<Integer> outArray = new ArrayList<Integer>();
		// Loop through the residues - this should be a list of lists
		
		for(List<Integer> resList: chainList){
			// CHAIN LEVEL COUNTERS
			int counter=0;
			int prevInd = 0;
			for(Integer i: resList){
				PDBGroup pdbGr= groupMap.get(i);
				List<Integer> bondInds = pdbGr.getBondIndices();
				for(int j=0; j<bondInds.size();j++){
					// If it's the C-of the petide bond (to next group) - set this as the 
					if (j==2){
						prevInd = counter+j;
					}
					// If it's the start of a group
					if(j==0){
						// If it's the very start of the model
						if(counter==0){
							// Just leave it as the Z coord 
							outArray.add(nfds.get_atom_site_Cartn_zInt().get(counter+j));
						}
						// If it only contains one atom
						else if (bondInds.size()==1){
							// Just leave it
							outArray.add(nfds.get_atom_site_Cartn_zInt().get(counter+j));
						}
						// If it's the peptide bond
						else{
						int bondDist = 1320;
						// NOW WE WAN'T THE NITROGEN FROM THE PREVIOUS GROUP
						int deltaDist = getDeltaDist(nfds, counter+j, prevInd, bondDist);
						outArray.add(deltaDist);
						}
					}
					else{
						int bondDist = pdbGr.getBondLengths().get(j);
						int deltaDist = getDeltaDist(nfds, counter+j, counter+bondInds.get(j), bondDist);
						if(deltaDist>50 || deltaDist<-50){
							System.out.println(deltaDist);
							System.out.println(pdbGr.getAtomInfo());
						}
						outArray.add(deltaDist);
	//					nfds.get_atom_site_Cartn_zInt().set(counter+j, deltaDist);
					}
				}
			counter += pdbGr.getBondIndices().size();
		}
		}
		nfds.set_atom_site_Cartn_zInt(outArray);
		return nfds;
	}

	private int getDeltaDist(NoFloatDataStruct nfds, int thisInd, int prevInd, int bondDist) {
		// TODO Auto-generated method stub
		// First find the distance between
		int thisDist = getEuclDist(nfds, thisInd, prevInd);

		return thisDist-bondDist;
	}

	private int getEuclDist(NoFloatDataStruct nfds, int thisInd, int prevInd) {
		// 
		double outAns = 0.0;
		double xDist=  nfds.get_atom_site_Cartn_xInt().get(thisInd)-nfds.get_atom_site_Cartn_xInt().get(prevInd);
		double yDist = nfds.get_atom_site_Cartn_yInt().get(thisInd)-nfds.get_atom_site_Cartn_yInt().get(prevInd);
		double zDist = nfds.get_atom_site_Cartn_zInt().get(thisInd)-nfds.get_atom_site_Cartn_zInt().get(prevInd);
		outAns += Math.pow(xDist, 2);
		outAns += Math.pow(yDist, 2);
		outAns += Math.pow(zDist, 2);
		// Set this as the dist
		return (int) Math.sqrt(outAns);
	}
	
}
