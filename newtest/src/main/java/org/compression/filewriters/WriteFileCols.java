package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.compression.structureholders.CoreSingleStructure;

public class WriteFileCols extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Map<String, Object> hmap = my_struct.getDataAsHashMap();
		hmap.remove("pdbCode");
		String pdb_code = my_struct.getStructureCode();
		OutputStream outStream = new ByteArrayOutputStream();
		outStream.write((pdb_code + "\n").getBytes());
		for (String key : hmap.keySet()) {
			outStream.write(key.getBytes());
			outStream.write(" ".getBytes());
			for (int i = 0; i < my_struct.getNumAtoms(); i++) {
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
