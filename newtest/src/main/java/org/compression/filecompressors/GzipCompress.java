package org.compression.filecompressors;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;
import java.io.FileInputStream;


public class GzipCompress extends AbstractFileCompressor implements FileCompressor {
	
	public void compressStream(InputStream fis, String file_path) throws IOException {
            FileOutputStream fos = new FileOutputStream(file_path+".gz");
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            writeToOutputStream(fis, gzipOS);

	}
	
	public void compressFile(String file_path) throws IOException {
            FileInputStream fis = new FileInputStream(file_path);
            FileOutputStream fos = new FileOutputStream(file_path+".gz");
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            writeToOutputStream(fis, gzipOS);

	}

}