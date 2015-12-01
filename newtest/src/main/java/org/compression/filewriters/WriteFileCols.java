package org.compression.filewriters;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.BioBean;
import org.compression.domstructureholders.CoreSingleStructure;

public class WriteFileCols extends AbstractDataWriter implements DataWriter {

	public OutputStream writeStream(CoreSingleStructure my_struct) throws IOException, JSONException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BioBean bioBean = my_struct.findDataAsBean();
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> hmap = m.convertValue(bioBean, Map.class);
		//System.out.println(hmap);
		// REMOVE THIS IF IT'S IN THERE
		if(hmap.containsKey("pdbCode")==true){
			hmap.remove("pdbCode");
		}
		String pdb_code = my_struct.findStructureCode();
		OutputStream outStream = new ByteArrayOutputStream();
//		PrintWriter writer = new PrintWriter("NEW_FILE_NAME_CHANGEME");
//		writer.
		outStream.write((pdb_code + "\n").getBytes());
		for (String key : hmap.keySet()) {
			System.out.println(key);
			if(hmap.get(key)==null){
				continue;
			}
			System.out.println(hmap.get(key).getClass().getTypeName());
			outStream.write(key.getBytes());
			outStream.write(" ".getBytes());
			for (int i = 0; i < my_struct.findNumAtoms(); i++) {
				outStream.write(String.valueOf(((ArrayList) hmap.get(key)).get(i)).getBytes());
				outStream.write(" ".getBytes());
			}
			outStream.write("\n".getBytes());

		}
		outStream.flush();
		outStream.close();
		return outStream;
	}
	
	public void writeBinaryFile(CoreSingleStructure my_struct, String fileName) throws IllegalAccessException, InvocationTargetException, IOException{
		// Get the data
		BioBean bioBean = my_struct.findDataAsBean();
		ObjectMapper m = new ObjectMapper();
		// Open the output stream
		DataOutputStream outStream = new DataOutputStream(new FileOutputStream(fileName));
		// Covert to a Hashmap - this is a terrible thing to do
		Map<String,Object> hmap = m.convertValue(bioBean, Map.class);	
		if(hmap.containsKey("pdbCode")==true){
			hmap.remove("pdbCode");
		}
		for (Entry<String,Object> myEntry : hmap.entrySet()) {
			outStream.writeUTF(myEntry.getKey());
			ArrayList myList = (ArrayList) myEntry.getValue();
			if(myList.get(0) instanceof Integer){
				for(int i=0;i<myList.size();i++){
					outStream.writeInt((int) myList.get(i));
				}
			}
			else if(myList.get(0) instanceof Double){
				for(int i=0;i<myList.size();i++){
					outStream.writeDouble((Double) myList.get(i));
				}				
			}
			else if(myList.get(0) instanceof String){
				for(int i=0;i<myList.size();i++){
					outStream.writeUTF((String) myList.get(i));
				}					
			}
			
		}
		
		
	}


}
