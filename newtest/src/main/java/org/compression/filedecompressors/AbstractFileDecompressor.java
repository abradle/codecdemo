package org.compression.filedecompressors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class AbstractFileDecompressor {

	
	
	public InputStream deCompressOutStream(InputStream compIn) throws IOException {
		// TODO Auto-generated method stub
		final byte[] buffer = new byte[1024];
		OutputStream outStream = new ByteArrayOutputStream();
		int n = 0;
		while (-1 != (n = compIn.read(buffer))) {
			  outStream.write(buffer, 0, n);
			}
		outStream.flush();
		outStream.close();
		return new ByteArrayInputStream(outStream.toString().getBytes());
	}
}
