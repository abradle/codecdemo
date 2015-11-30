package org.compression.filewriters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.CoreSingleStructure;

public interface DataWriter {
	// Function for a DataWriter
	public void writeFile(String file_path, CoreSingleStructure my_struct) throws IOException, JSONException,IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	public InputStream writeInStream(CoreSingleStructure my_struct) throws IOException, JSONException,  IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

}
