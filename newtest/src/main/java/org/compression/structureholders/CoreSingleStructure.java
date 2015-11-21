package org.compression.structureholders;

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
	Map<String, Object> getDataAsHashMap();
	// Return the data as a BioJava structure object
	Structure getDataAsBioJava();
	// Return the data as a JSON
	JSONObject getDataAsJSON() throws JSONException;
	// Return the strign
	String getStructureCode();
	// Code to set each of these attributes
	void setDataAsHashMap(HashMap<String, ArrayList> hmap);
	// Set the data as a BioJava structure object
	void setDataAsBioJava(Structure in_struct);
	// Set the data as a JSON
	void setDataAsJSON(JSONObject in_json);
	// Set the code of this data object
	void setStructureCode(String my_code);
	int getNumAtoms();
	void fillBioDataStruct(String key, Object part);}
