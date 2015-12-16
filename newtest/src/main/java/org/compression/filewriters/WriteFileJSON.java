package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.compression.domstructureholders.BioBean;
import org.compression.domstructureholders.CoreSingleStructure;
import org.msgpack.jackson.dataformat.MessagePackFactory;

public class WriteFileJSON extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException, IllegalArgumentException, IllegalAccessException,  NoSuchMethodException, InvocationTargetException {
		// TODO Auto-generated method stub
		BioBean bioBean = my_struct.findDataAsBean();
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> hMap = m.convertValue(bioBean, Map.class);
		JSONObject myJson = new JSONObject(hMap);
		OutputStream outStream = new ByteArrayOutputStream();
		outStream.write(myJson.toString().getBytes());
		outStream.flush();
		outStream.close();
		return outStream;
	}
	
}