package org.compression.biocodec;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.biojava.nbio.structure.StructureException;
import org.compression.biocompressor.BioCompressor;
import org.compression.biocompressor.CompressDoubles;
import org.compression.biocompressor.CompressResidues;
import org.compression.biodecompression.BioDeCompressor;
import org.compression.biodecompression.DeCompressDoubles;
import org.compression.biodecompression.DeCompressResidues;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NoFloatDataStruct;
import org.junit.Test;



public class TestBioCodec extends AbstractTestBioCodec {

	@Test
	public void testCompressDoubles() throws IllegalAccessException, InvocationTargetException, IOException, StructureException {
		BioDataStruct coress = (BioDataStruct) getStructureInput("BioDataStruct");
		BioCompressor bioComp = new CompressDoubles();
		BioDeCompressor bioDeComp = new DeCompressDoubles();
		BioDataStruct procCoreSS = (BioDataStruct) testStructs(bioComp, bioDeComp, coress);
		// Check the data has been restored
		assertTrue(findDiffString(coress.get_atom_site_asym_id(),procCoreSS.get_atom_site_asym_id()));
		assertTrue(findDiffDouble(coress.get_atom_site_Cartn_x(),procCoreSS.get_atom_site_Cartn_x()));
		assertTrue(findDiffDouble(coress.get_atom_site_Cartn_y(),procCoreSS.get_atom_site_Cartn_y()));
		assertTrue(findDiffDouble(coress.get_atom_site_Cartn_z(),procCoreSS.get_atom_site_Cartn_z()));
		assertTrue(findDiffDouble(coress.get_atom_site_B_iso_or_equiv(),procCoreSS.get_atom_site_B_iso_or_equiv()));
	}
	
	@Test
	public void testCompressResidues() throws IllegalAccessException, InvocationTargetException, IOException, StructureException {
		NoFloatDataStruct coress = (NoFloatDataStruct) getStructureInput("NoFloatDataStruct");
		BioCompressor bioComp = new CompressResidues();
		BioDeCompressor bioDeComp = new DeCompressResidues();
		NoFloatDataStruct procCoreSS = (NoFloatDataStruct) testStructs(bioComp, bioDeComp, coress);
		
		// Check these are correctly transferred back
		assertTrue(findDiffString(coress.get_atom_site_symbol(),procCoreSS.get_atom_site_symbol()));
		assertTrue(findDiffString(coress.get_atom_site_group_PDB(),procCoreSS.get_atom_site_group_PDB()));
		assertTrue(findDiffString(coress.get_atom_site_label_atom_id(),procCoreSS.get_atom_site_label_atom_id()));
		assertTrue(findDiffString(coress.get_atom_site_label_comp_id(),procCoreSS.get_atom_site_label_comp_id()));
	}
	
	
	private boolean findDiffDouble(List<Double> listOne, List<Double> listTwo) {
		// TODO Auto-generated method stub
		double outSum = 0.0;
		for(int i=0; i<listOne.size();i++){
			if(listOne.get(i).equals(listTwo.get(i))==false){			
				outSum+=Math.abs(listOne.get(i)-listTwo.get(i));
			}
		}
		System.out.println("AVERAGE ERROR");
		System.out.println(outSum/listOne.size());	
		if(outSum/listOne.size()>0.001){
			return false;
		}
		return true;
	}


	private boolean findDiffFloat(List<Float> listOne, List<Float> listTwo) {
		// TODO Auto-generated method stub
		float outSum = (float) 0.0;
		for(int i=0; i<listOne.size();i++){
			if(listOne.get(i).equals(listTwo.get(i))==false){
				System.out.println(listOne.get(i));
				System.out.println(listTwo.get(i));				
				outSum+=Math.abs(listOne.get(i)-listTwo.get(i));
			}
		}
		System.out.println("AVERAGE ERROR");
		System.out.println(outSum/listOne.size());	
		if(outSum>0.01){
			return false;
		}
		return true;
		
	}


	private boolean findDiffString(List<String> listOne, List<String> listTwo) {
		// TODO Auto-generated method stub
		for(int i=0; i<listOne.size();i++){
			if(listOne.get(i).equals(listTwo.get(i))==false){
				System.out.println(listOne.get(i));
				System.out.println(listTwo.get(i));		
				return false;
			}
		}
		return true;
		
	}


	private CoreSingleStructure testStructs(BioCompressor bioComp, BioDeCompressor bioDeComp, CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException {
		// Fist compress
		CoreSingleStructure compStruct = bioComp.compresStructure(coress);
		CoreSingleStructure decompStruct = bioDeComp.deCompresStructure(compStruct);
		return decompStruct;
	}

}
