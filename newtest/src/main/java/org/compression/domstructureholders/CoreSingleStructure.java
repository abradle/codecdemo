package org.compression.domstructureholders;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureImpl;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface CoreSingleStructure {
	// Interface to deal with core structural data
	// Return the data as a HashMap
	BioBean findDataAsBean() throws IllegalAccessException, InvocationTargetException;
	Map<String, Object> findDataAsHashMap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	// Return the data as a BioJava structure object
	Structure findDataAsBioJava();
	// Return the data as a JSON
	JSONObject findDataAsJSON() throws JSONException,IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	// Return the strign
	String findStructureCode();
	// Return the biodatastruct
	BioDataStruct findDataAsBioDataStruct();
	// Return the data as a nonFloatStruct
	NoFloatDataStruct findDataAsNoFloatStruct();
	// Set the code of this data object
	void setStructureCode(String my_code);
	int findNumAtoms();
	void fillDataStruct(String key, Object part);	
}
