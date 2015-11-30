package org.compression.filereaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.biojava.nbio.structure.StructureException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;

public class ReadJSON extends AbstractDataReader implements DataReader {

	public CoreSingleStructure readFile(String file_path) throws IOException, StructureException, JSONException {
		
		
		return readStream(new FileInputStream(file_path));


	}

	public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException, StructureException {
		JSONObject jsonObject  = null;
		try {		
			String my_s = IOUtils.toString(input_stream, "UTF-8");
			jsonObject = new JSONObject(my_s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Need to find the in_code => should be 
		CoreSingleStructure bioStruct = new BioDataStruct();
		bioStruct.setStructureCode(jsonObject.getString("pdbCode"));
		// Remove this key
		jsonObject.remove("pdbCode");
		Iterator<?> keys = jsonObject.keys();
		while( keys.hasNext() ) {
		    String key = (String)keys.next();
		    String[] parts = jsonObject.getString(key).replace("[", "").replace("]", "").split(",");
		    int i =0;
		    for (Object part : parts) {
			    bioStruct.fillDataStruct(key, part);
			}
		}
		return bioStruct;
	}
}
