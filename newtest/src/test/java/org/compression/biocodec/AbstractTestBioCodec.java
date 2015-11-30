package org.compression.biocodec;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.biojava.nbio.structure.StructureException;
import org.compression.biocompressor.BioCompressor;
import org.compression.biocompressor.CompressDoubles;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;

public class AbstractTestBioCodec {
	// Function to get a structure to the appropriate positions
	public CoreSingleStructure getStructureInput(String inputName) throws IOException, StructureException, IllegalAccessException, InvocationTargetException{
		// Get the basic type
		BioDataStruct bdh = new BioDataStruct("1QMZ");
		if(inputName=="BioDataStruct"){
			
			return bdh;
		}
		else if(inputName=="NoFloatDataStruct"){
			BioCompressor compDubs = new CompressDoubles();
			return compDubs.compresStructure(bdh);
		}
		else{
			System.out.println("TARGET NAME NOT RECOGNISED");
			return null;
		}
		
		
	}
	
	
}
