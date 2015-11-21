package org.compression.filecompressors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public interface 	FileCompressor {
	
	public void compressFile(String file_path) throws FileNotFoundException, IOException;
	public void compressStream(InputStream input_stream, String out_path) throws FileNotFoundException, IOException;

}
