package org.compression.filedecompressors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.compression.filecompressors.AbstractFileCompressor;

public class Bzip2DeCompress extends AbstractFileDecompressor implements FileDeCompressor{

	public InputStream decompressFile(String file_path) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return decompressStream(new FileInputStream(file_path));
	}

	public InputStream decompressStream(InputStream input_stream) throws FileNotFoundException, IOException {
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(input_stream);
		return deCompressOutStream(bzIn);
	}
	
}