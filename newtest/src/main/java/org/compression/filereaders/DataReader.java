package org.compression.filereaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.biojava.nbio.structure.StructureException;
import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.CoreSingleStructure;

public interface DataReader {
	// Readers return CoreSingleStructures
	public CoreSingleStructure readFile(String file_path) throws FileNotFoundException, IOException, StructureException, JSONException;
	public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException, StructureException;
}
