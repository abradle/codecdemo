package org.compression.filewriters;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.compression.domstructureholders.CoreSingleStructure;

public class AbstractDataWriter {
	// A template class 
	
	public void writeFile(String file_path, CoreSingleStructure my_struct) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException {
		// Get the output stream and write it
		OutputStream outStream = writeStream(my_struct);
		PrintWriter writer = new PrintWriter(new File(file_path));
		writer.write(outStream.toString());
		writer.close();
	}
	public InputStream writeInStream(CoreSingleStructure my_struct) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException  {
		// Function to create an input stream from an output stream
		return new ByteArrayInputStream(writeStream(my_struct).toString().getBytes());
	}
	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		// TODO Auto-generated method stub
		System.out.println("FUNCTION NOT PREPERAED");
		return null;
	}
	
	private static String getTempFilePath()

	{

		try {

			// create a temp file
			File temp = File.createTempFile("temp-file-name", ".tmp");

			System.out.println("Temp file : " + temp.getAbsolutePath());

			// Get tempropary file path
			String absolutePath = temp.getAbsolutePath();
			String tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
			return tempFilePath;

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	private void writeJsonToFile(String file_path, JSONObject my_json) {
		FileWriter jd_file;
		try {
			jd_file = new FileWriter(file_path);
			jd_file.write(my_json.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			jd_file.flush();
			jd_file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
