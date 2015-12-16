package org.compression.filecompressors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

//import org.scijava.nativelib.NativeLoader;
//
//import de.bitkings.jbrotli.Brotli;
//import de.bitkings.jbrotli.BrotliCompressor;
//import de.bitkings.jbrotli.BrotliDeCompressor;

public class BrotliCompress extends AbstractFileCompressor implements FileCompressor {

	@Override
	public void compressFile(String file_path) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void compressStream(InputStream input_stream, String out_path) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}
	
//	public void compressByteArray() throws IOException{
//		NativeLoader.loadLibrary("brotli");
//		String inString = "Brotli: a new compression algorithm for the internet. Now available for Java!kdkkdddddddddk";
//		byte[] inBuf = inString.getBytes();
//		System.out.println(inBuf.length);
//		byte[] compressedBuf = new byte[2048];
//		byte[] decompressedBuf = new byte[2048];
//		BrotliCompressor compressor = new BrotliCompressor();
//		int outLength = compressor.compress(Brotli.DEFAULT_PARAMETER, inBuf, compressedBuf);
//		System.out.println(outLength);
//		BrotliDeCompressor decompressor = new BrotliDeCompressor();
//		decompressor.deCompress(compressedBuf, decompressedBuf);
//		String newString = new String(decompressedBuf);
//		System.out.println(newString.equals(inString));
//		System.out.println(newString);
//		System.out.println(inString);
//		
//	}

}
