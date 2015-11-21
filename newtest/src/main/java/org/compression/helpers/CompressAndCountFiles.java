package org.compression.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * This class defines two static methods for gzipping files and zipping
 * directories. It also defines a demonstration program as a nested class.
 */
public class CompressAndCountFiles {
  /** Gzip the contents of the from file and save in the to file. */
	public static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
	public static double getFileSize(String file_path)
    {	
		File file =new File(file_path);
		
		if(file.exists()){
			
			double bytes = file.length();				
			System.out.println("bytes : " + bytes);
			return bytes;
		}else{
			 System.out.println("File does not exists!");
			 return 0.0;
		}
    		
    }

	
	public static double getFolderSize(String directory_path) {
		// Function to return the size of folder
	    double length = 0;
	    File directory =new File(directory_path);
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += getFolderSize(file.getAbsolutePath());
	    }
	    return length;
	}
	
	
    public static double compressGZipDirectory(String dir) throws IOException,
      IllegalArgumentException {
    // Check that the directory is a directory, and get its contents
    File d = new File(dir);
    if (!d.isDirectory())
      throw new IllegalArgumentException("Compress: not a directory:  " + dir);
    String[] entries = d.list();
    byte[] buffer = new byte[4096]; // Create a buffer for copying
    int bytes_read;

    // Create a stream to compress data and write it to the zipfile
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(dir+".gz"));

    // Loop through all entries in the directory
    for (int i = 0; i < entries.length; i++) {
      File f = new File(d, entries[i]);
      if (f.isDirectory())
        continue; // Don't zip sub-directories
      FileInputStream in = new FileInputStream(f); // Stream to read file
      ZipEntry entry = new ZipEntry(f.getPath()); // Make a ZipEntry
      out.putNextEntry(entry); // Store entry
      while ((bytes_read = in.read(buffer)) != -1)
        // Copy bytes
        out.write(buffer, 0, bytes_read);
      in.close(); // Close input stream
    }
    return getFileSize(dir+".gz");
    }
    
    
	public static double compressGzipFile(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file+".gz");
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return getFileSize(file+".gz");
    }
}
