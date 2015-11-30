package org.compression.biocompressor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NonAtomDataStruct;
import org.compression.domstructureholders.ResidueGraphDataStruct;

public class CompressResidues  extends AbstractBioCompressor implements BioCompressor {

	public CoreSingleStructure compresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException {
		// The in object
		ResidueGraphDataStruct resGraphDS = new ResidueGraphDataStruct();
		NonAtomDataStruct noatomDS = (NonAtomDataStruct) coress;
		updateInfo(resGraphDS, coress, noatomDS);
		return resGraphDS;
	}



}
