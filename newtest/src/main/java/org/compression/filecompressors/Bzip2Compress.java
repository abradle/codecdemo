package org.compression.filecompressors;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public class Bzip2Compress extends AbstractFileCompressor implements FileCompressor {

	public void compressStream(InputStream fis, String file_path) throws IOException {
        FileOutputStream fos = new FileOutputStream(file_path+".bzip2");
        BZip2CompressorOutputStream gzipOS = new BZip2CompressorOutputStream(fos);
        writeToOutputStream(fis, gzipOS);
		
	}

	public void compressFile(String file_path) throws IOException {
        FileInputStream fis = new FileInputStream(file_path);
        FileOutputStream fos = new FileOutputStream(file_path+".bzip2");
        BZip2CompressorOutputStream gzipOS = new BZip2CompressorOutputStream(fos);			
		writeToOutputStream(fis, gzipOS);
	}
	
}