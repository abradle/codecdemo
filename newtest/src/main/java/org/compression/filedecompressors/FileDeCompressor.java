package org.compression.filedecompressors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public interface FileDeCompressor {
	
	public InputStream decompressFile(String file_path) throws FileNotFoundException, IOException;
	public InputStream decompressStream(InputStream input_stream) throws FileNotFoundException, IOException;
	
}
