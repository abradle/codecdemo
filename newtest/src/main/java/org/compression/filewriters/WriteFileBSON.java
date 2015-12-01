package org.compression.filewriters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.bson.BSON;
import org.bson.BSONDecoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.compression.domstructureholders.BioBean;
import org.compression.domstructureholders.CoreSingleStructure;

import de.undercouch.bson4jackson.BsonFactory;
import de.undercouch.bson4jackson.BsonGenerator;
import de.undercouch.bson4jackson.BsonModule;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WriteFileBSON extends AbstractDataWriter implements DataWriter {
	public OutputStream writeStream(CoreSingleStructure my_struct) throws IllegalAccessException, InvocationTargetException, IOException {
	// Get the data as a beanß
	BioBean bioBean = my_struct.findDataAsBean();
	ObjectMapper m = new ObjectMapper();
	// Open the output stream
	OutputStream outStream = new ByteArrayOutputStream();
	// Covert to a Hashmap - this is a terrible thing to do
	Map<String,Object> hmap = m.convertValue(bioBean, Map.class);
	BSONObject myBSON = null;
	try {
		myBSON = generateAndParse(hmap);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	outStream.write(BSON.encode(myBSON));
	return outStream;
	}
	
	public void writeBinaryFile(String fileName, CoreSingleStructure my_struct) throws IOException, IllegalAccessException, InvocationTargetException{
		// Get the data as a beanß
		BioBean bioBean = my_struct.findDataAsBean();
		ObjectMapper m = new ObjectMapper();
		// Open the output stream
		//OutputStream outStream = new ByteArrayOutputStream();
		DataOutputStream outStream = new DataOutputStream(new FileOutputStream(fileName));
		// Covert to a Hashmap - this is a terrible thing to do
		Map<String,Object> hmap = m.convertValue(bioBean, Map.class);
		BSONObject myBSON = null;
		try {
			myBSON = generateAndParse(hmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outStream.write(BSON.encode(myBSON));
	}
	
	private BSONObject generateAndParse(Map<String, Object> data,
			BsonGenerator.Feature... featuresToEnable) throws JsonGenerationException, JsonMappingException, IOException  {
		// Function to parse a BSON from a map
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BsonFactory bsonFactory = new BsonFactory();
		if (featuresToEnable != null) {
			for (BsonGenerator.Feature fe : featuresToEnable) {
				bsonFactory.enable(fe);
			}
		}
		ObjectMapper om = new ObjectMapper(bsonFactory);
		om.registerModule(new BsonModule());
		om.writeValue(baos, data);

		byte[] r = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(r);

		BSONDecoder decoder = new BasicBSONDecoder();
		return decoder.readObject(bais);
	}

}
