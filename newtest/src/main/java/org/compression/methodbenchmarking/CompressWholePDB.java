package org.compression.methodbenchmarking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.compression.filecompressors.SquashBenchmark;

public class CompressWholePDB {
	
	
	public static void main(String[] args){
		SquashBenchmark sb = new SquashBenchmark();
		getPDBFilePaths();
		// Now loop through all the PDB ids
		//sb.brotliCompression(inFile, outFile);
	}
	
	
    private static void findFile(String basePath)
    {
    	File file = new File(basePath);
        File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list)
        {
            if (fil.isDirectory())
            {
                findFile(fil.getName());
            }
            else if (fil.getName().endsWith(".mmcif.gz"))
            {
                System.out.println(fil.getAbsolutePath());
            }
        }
    }
	private static void getPDBFilePaths(){
		String basePath = "/Users/anthony/PDB_CACHE/data/structures/divided/mmCIF/";
		findFile(basePath);
//		basePath +=pdbCode.charAt(1);
//		basePath +=pdbCode.charAt(2);
//		// Send this to lower case
//		basePath +="/"+pdbCode.toLowerCase()+".cif.gz";
//		return basePath;
	}
	
	private void gunzipIt(String inFile, String outFile){
		 
	     byte[] buffer = new byte[1024];
	 
	     try{
	 
	    	 GZIPInputStream gzis = 
	    		new GZIPInputStream(new FileInputStream(inFile));
	 
	    	 FileOutputStream out = 
	            new FileOutputStream(outFile);
	 
	        int len;
	        while ((len = gzis.read(buffer)) > 0) {
	        	out.write(buffer, 0, len);
	        }
	 
	        gzis.close();
	    	out.close();
	 
	    	System.out.println("Done");
	    	
	    }catch(IOException ex){
	       ex.printStackTrace();   
	    }
	   } 
	
	
}
