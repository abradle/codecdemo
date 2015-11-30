package org.compression.biocompressor;

import java.lang.reflect.InvocationTargetException;

import org.compression.domstructureholders.CoreSingleStructure;

public interface BioCompressor {
	
	public CoreSingleStructure compresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException;

}
