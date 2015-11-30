package org.compression.filesize;

import java.io.File;


public class CheckFileSize {
	public double getFileSize(String filePath){
		File file =new File(filePath);

		if(file.exists()){

		double bytes = file.length();
		return bytes;
		}
		else{
			return 0.0;
		}
	}
}
