package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.BioBean;
import org.compression.domstructureholders.CoreSingleStructure;
public class WriteFileRows extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// Function to write a file as Rows
		BioBean bioBean = my_struct.findDataAsBean();
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> hmap = m.convertValue(bioBean, Map.class);
		OutputStream outStream = new ByteArrayOutputStream();
		String pdb_code = my_struct.findStructureCode();
		String[] myArray = hmap.keySet().stream().toArray(String[]::new);
		for(String k: myArray){
			if(k.startsWith("_")!=true){
				hmap.remove(k);
			}
		}
		try {
			// First we write down all the columns
			outStream.write((pdb_code + "\n").getBytes());
			// NOW REMOVE THAT KEY
			for (String key : hmap.keySet()) {
				outStream.write(key.getBytes());
				outStream.write("\n".getBytes());
			}
			String myKey = (String) hmap.keySet().toArray()[0];
			// Second we fill out each of the rows
			for (int i = 0; i < my_struct.findNumAtoms(); i++) {
				for (String key : hmap.keySet()) {
					try {
						outStream.write(String.valueOf(((ArrayList) hmap.get(key)).get(i)).getBytes());
						outStream.write(" ".getBytes());
					} catch (Exception IndexOutOfBoundsException) {
					}

				}
				outStream.write("\n".getBytes());
			}
			// Finally we close it down
			outStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outStream.flush();
		outStream.close();
		return outStream;
	}


}