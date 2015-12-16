package org.compression.filecompressors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compression.filesize.CheckFileSize;

public class SquashBenchmark {
	public Map<String, Double> benchmarkCompression(String filePath) throws IOException, InterruptedException{
		Map<String, Double> resultMap = new HashMap<String, Double>();
		CheckFileSize cfs = new CheckFileSize();
		// Now run the compressions
		for(String codec: getCodecList()){
			String printString = "/usr/local/bin/squash -f -k -c "+codec+" "+filePath+"  "+filePath+"."+codec;
			//System.out.println(printString);
			Process p = Runtime.getRuntime().exec(printString);
			p.waitFor();
			// Puts the result - comparison is to uncompressed file
			resultMap.put(codec, cfs.getFileSize(filePath+"."+codec));
		}
		return resultMap;
	}
	
	public void brotliCompression(String inFile, String outFile) throws IOException, InterruptedException{
		String printString = "/usr/local/bin/squash -f -k -c brotli "+inFile+"  "+outFile;
		Process p = Runtime.getRuntime().exec(printString);
		p.waitFor();
	}
	
	private List<String> getCodecList(){
		// Set up the different compressions
		List<String> myList = new ArrayList<String>();
		myList.add("brieflz");
		myList.add("brotli");
		myList.add("bsc");
		myList.add("bzip2");
		myList.add("compress");
		myList.add("copy");
		myList.add("crush");
		myList.add("deflate");
		myList.add("density");
		myList.add("fari");
		myList.add("fastlz");
		myList.add("gipfeli");
		myList.add("gzip");
		myList.add("heatshrink");
		myList.add("lz4");
		myList.add("lz4f");
		myList.add("lzf");
		myList.add("lzg");
		myList.add("lzham");
		myList.add("lzjb");
		myList.add("lzma");
		myList.add("lzma1");
		myList.add("lzma2");
		myList.add("lznt1");
		myList.add("lzo1b");
		myList.add("lzo1c");
		myList.add("lzo1f");
		myList.add("lzo1x");
		myList.add("lzo1y");
		myList.add("lzo1z");
		myList.add("pithy");
		myList.add("quicklz");
		myList.add("snappy");
		myList.add("wflz");
		myList.add("wflz-chunked");
		myList.add("xpress");
		myList.add("xpress-huffman");
		myList.add("xz");
		myList.add("yalz77");
		myList.add("zlib");
		myList.add("zling");
		myList.add("zpaq");
		myList.add("zstd");
		return myList;
		
	}
}
