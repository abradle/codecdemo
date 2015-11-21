package org.compression.filedecompressors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GzipDeCompress extends AbstractFileDecompressor implements FileDeCompressor{

	public InputStream decompressFile(String file_path) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return decompressStream(new FileInputStream(file_path));
	}

	public InputStream decompressStream(InputStream input_stream) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		GZIPInputStream compIn = new GZIPInputStream(input_stream);
		return deCompressOutStream(compIn);
	}
	
}
