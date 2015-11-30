package org.compression.domstructureholders;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.biojava.nbio.structure.Structure;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class NoFloatDataStruct extends NoFloatDataStructBean implements CoreSingleStructure {

	public BioBean findDataAsBean() throws IllegalAccessException, InvocationTargetException {
		// Cast this to the pure data
		NoFloatDataStructBean newData = new NoFloatDataStructBean();
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

		return this;
	}

	public void setStructureCode(String my_code) {
		// TODO Auto-generated method stub
		
	}

	public int findNumAtoms() {
		return get_atom_site_Cartn_xInt().size();
	}

	public void fillDataStruct(String key, Object part) {
		// TODO Auto-generated method stub
		
	}

}
