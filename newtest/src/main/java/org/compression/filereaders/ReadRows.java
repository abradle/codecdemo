package org.compression.filereaders;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONException;
import org.compression.structureholders.BasicDataHolder;
import org.compression.structureholders.BioDataStruct;
import org.compression.structureholders.CoreSingleStructure;

public class ReadRows extends AbstractDataReader implements DataReader {

	public CoreSingleStructure readFile(String file_path) throws IOException, JSONException {

		
		return readStream(new FileInputStream(file_path));

	}

	public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException {
		// TODO Auto-generated method stub
		// Function to get a Hashmap from a file written out where each line
		// is a new key
		BioDataStruct bioStruct = new BioDataStruct();
		CoreSingleStructure bdh = new BasicDataHolder(bioStruct);
		ArrayList<String> my_list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(input_stream));
		boolean line_counter =true;
		for (String line; (line = br.readLine()) != null;) {			
			// Read the header
			if(line_counter== true){
				// Alter this
				line_counter =false;
				bdh.setStructureCode(line);
			}
			// process the line.
			else if (line.startsWith("_")) {
				my_list.add(line);
			} else {
				// Split the line and add the data
				String[] parts = line.split(" ");
				int counter = 0;
				for (Object part : parts) {
					String key = my_list.get(counter);
					bdh.fillBioDataStruct(key, part);
					counter += 1;
				}
			}
		}
		// Need to find the in_code => should be 
		return bdh;
	}
}
