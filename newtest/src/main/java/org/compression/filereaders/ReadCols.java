package org.compression.filereaders;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;


public class ReadCols extends AbstractDataReader implements DataReader {

	public CoreSingleStructure readFile(String file_path) throws IOException, JSONException {
		// Read a file by columnns
		return readStream(new FileInputStream(file_path));
	}

	public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input_stream));
		
		// So this means we can only read in and out BioDataStruct....
		CoreSingleStructure bioStruct = new BioDataStruct();
		boolean line_counter = true;
		String pdb_code = null;
		for (String line; (line = br.readLine()) != null;) {
			// process the line - this time we just split
			String[] parts = line.split(" ");
			int counter = 0;
			String key = null;
			
			if (line_counter==true){
				bioStruct.setStructureCode(line);
				line_counter=false;
			}
			for (Object part : parts) {
				if (counter == 0) {
					key = (String) part;
				} else {
					bioStruct.fillDataStruct(key, part);
				}
				counter += 1;
			}
		};
		return bioStruct;
	}

}

