package org.compression.biodecompression;

import java.lang.reflect.InvocationTargetException;

import org.compression.domstructureholders.CoreSingleStructure;

public interface BioDeCompressor {
	
	public CoreSingleStructure deCompresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException;

}
