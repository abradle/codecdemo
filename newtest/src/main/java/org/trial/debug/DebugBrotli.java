//package org.trial.debug;
//
//import java.io.IOException;
//import org.scijava.nativelib.NativeLoader;
//
//import de.bitkings.jbrotli.Brotli;
//import de.bitkings.jbrotli.BrotliCompressor;
//
//public class DebugBrotli {
//
//	public void brotliBuild(int numChars) throws IOException{
//		//
//		BrotliCompressor compressor = new BrotliCompressor();
//		StringBuilder bigData = new StringBuilder();
//		for (int i=0; i<numChars;i++){
//			bigData.append(i);
//		}
//		byte[] inBuf = bigData.toString().getBytes();
//		System.out.println(inBuf.length);
//		byte[] compressedBuf = new byte[2048];
//		int outLength = compressor.compress(Brotli.DEFAULT_PARAMETER, inBuf, compressedBuf);
//		System.out.println(outLength);
//	}
//	
//	
//	
//	public static void main(String[] args) throws IOException{
//		NativeLoader.loadLibrary("brotli");
//		DebugBrotli db = new DebugBrotli();
//		db.brotliBuild(100000);
//		db.brotliBuild(100000);
//	}
//}
