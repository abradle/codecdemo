package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.compression.structureholders.BasicDataHolder;
import org.compression.structureholders.BioDataStruct;
import org.compression.structureholders.CoreSingleStructure;

public class WriteFileJSON extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException {
		// TODO Auto-generated method stub
		JSONObject my_json = my_struct.getDataAsJSON();
		OutputStream outStream = new ByteArrayOutputStream();
		outStream.write(my_json.toString().getBytes());
		outStream.flush();
		outStream.close();
		return outStream;
	}
	
}