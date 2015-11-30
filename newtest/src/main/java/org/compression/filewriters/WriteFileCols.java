package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.BioBean;
import org.compression.domstructureholders.CoreSingleStructure;

public class WriteFileCols extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BioBean bioBean = my_struct.findDataAsBean();
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> hmap = m.convertValue(bioBean, Map.class);
		//System.out.println(hmap);
		// REMOVE THIS IF IT'S IN THERE
		if(hmap.containsKey("pdbCode")==true){
			hmap.remove("pdbCode");
		}
		String pdb_code = my_struct.findStructureCode();
		OutputStream outStream = new ByteArrayOutputStream();
		outStream.write((pdb_code + "\n").getBytes());
		for (String key : hmap.keySet()) {
			System.out.println(key);
			if(hmap.get(key)==null){
				continue;
			}
			System.out.println(hmap.get(key).getClass().getTypeName());
			outStream.write(key.getBytes());
			outStream.write(" ".getBytes());
			for (int i = 0; i < my_struct.findNumAtoms(); i++) {
				outStream.write(String.valueOf(((ArrayList) hmap.get(key)).get(i)).getBytes());
				outStream.write(" ".getBytes());
			}
			outStream.write("\n".getBytes());

		}
		outStream.flush();
		outStream.close();
		return outStream;
	}


}
