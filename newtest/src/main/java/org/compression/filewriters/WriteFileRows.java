package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.compression.structureholders.CoreSingleStructure;
public class WriteFileRows extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// Function to write a file as Rows
		Map<String, Object> hmap = my_struct.getDataAsHashMap();
		OutputStream outStream = new ByteArrayOutputStream();
		String pdb_code = my_struct.getStructureCode();
		hmap.remove("pdbCode");
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
			for (int i = 0; i < my_struct.getNumAtoms(); i++) {
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