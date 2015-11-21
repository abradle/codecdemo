package org.compression.filereaders;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Element;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.ResidueNumber;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.bson.BasicBSONObject;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.rcsb.spark.util.SparkUtils;
import com.devsmart.ubjson.UBReader;

public class AbstractDataReader {

//	public class ReadBSON implements DataReader {
//
//		public CoreSingleStructure readFile(String file_path) throws JSONException {
////			HashMap<String, ArrayList> hmap = makeHmap();
////			// First write the JSON
////			System.out.println("FUNCTION NOT DONE");
////			CoreSingleStructure coress = new BasicDataHolder(hmap, (String) hmap.get_pdb_code").get(0));
////			return coress;
//			return null;
//		}
//
//		public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	}
//
//	public class ReadUBJSON implements DataReader {
//
//		public CoreSingleStructure readFile(String file_path) throws JSONException {
////			HashMap<String, ArrayList> hmap = makeHmap();
////			// First write the JSON
////			System.out.println("FUNCTION NOT DONE");
////			CoreSingleStructure coress = new BasicDataHolder(hmap, (String) hmap.get_pdb_code").get(0));
//			return null;
//		}
//
//		public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	}
//
//	public class ReadParquet implements DataReader {
//
//		public CoreSingleStructure readFile(String file_path) throws JSONException {
//			
////			HashMap<String, ArrayList> hmap = makeHmap();
////			// First write the JSON
////			System.out.println("FUNCTION NOT DONE");
////			CoreSingleStructure coress = new BasicDataHolder(hmap, (String) hmap.get_pdb_code").get(0));
//			return null;
//		}
//
//		public CoreSingleStructure readStream(InputStream input_stream) throws IOException, JSONException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//		
//	}

    public String getTempFilePath(){
	try{
		
		//create a temp file
		File temp = File.createTempFile("temp-file-name", ".tmp"); 
	//Get tempropary file path
		String absolutePath = temp.getAbsolutePath();
		String tempFilePath = absolutePath.
		    substring(0,absolutePath.lastIndexOf(File.separator));
		return absolutePath;
	}catch(IOException e){
		
		e.printStackTrace();
	}
	return null;
	
	}

    static String readThisFile(String path, Charset encoding) 
    		  throws IOException 
    		{
    		  byte[] encoded = Files.readAllBytes(Paths.get(path));
    		  return new String(encoded, encoding);
    		}
    
}
