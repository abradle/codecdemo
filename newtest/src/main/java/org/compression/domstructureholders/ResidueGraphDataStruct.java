package org.compression.domstructureholders;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.biojava.nbio.structure.Structure;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ResidueGraphDataStruct  extends ResidueGraphDataStructBean implements CoreSingleStructure {

	public ResidueGraphDataStructBean findDataAsBean() throws IllegalAccessException, InvocationTargetException{
		// Cast this to the pure data
		ResidueGraphDataStructBean newData = new ResidueGraphDataStructBean();
		BeanUtils bu = new BeanUtils();
		bu.copyProperties(newData, this);
		return newData;
	}


	public Map<String, Object> findDataAsHashMap()
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return null;
	}

	public Structure findDataAsBioJava() {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject findDataAsJSON() throws JSONException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return null;
	}

	public String findStructureCode() {
		// TODO Auto-generated method stub
		return null;
	}

	public BioDataStruct findDataAsBioDataStruct() {
		// TODO Auto-generated method stub
		return null;
	}

	public NoFloatDataStruct findDataAsNoFloatStruct() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStructureCode(String my_code) {
		// TODO Auto-generated method stub
		
	}

	public int findNumAtoms() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void fillDataStruct(String key, Object part) {
		// TODO Auto-generated method stub
		
	}

}
