package org.compression.methodbenchmarking;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.filesize.CheckFileSize;
import org.json.simple.JSONObject;

public class BenchmarkRun {

		public JSONObject getResultsPDB(String pdbCode) throws IOException, StructureException,IllegalAccessException, InvocationTargetException, NoSuchMethodException, JSONException, InterruptedException{		
		// Try for multiple structures
//		BioDataStruct bigStruct = new BioDataStruct("1FFK");
//		String smallStruct = "4CUP";
//		BioDataStruct nmrStruct = new BioDataStruct("1G03");
		//BioDataStruct enormousStruct = new BioDataStruct("4u4o");
		Map<String, Map<String, Double>> outMap = runRecipes(pdbCode);
		// Now set up the results string
		PrintWriter writer = new PrintWriter("OUTRESULTS.json", "UTF-8");
		JSONObject outJson = new JSONObject(outMap);
		writer.write(outJson.toString());
		return outJson;
	}
		
		private Map<String, Map<String, Double>> runRecipes(String pdbCode) throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException, IllegalAccessException{
			BenchmarkRecipes br = new BenchmarkRecipes();
			BioDataStruct bdh = new BioDataStruct(pdbCode);
			// The overall results
			Map<String, Map<String, Double>> outMap = new HashMap<String, Map<String, Double>>();
			Double origSize = getOrigFileSize(pdbCode);
			outMap.put("1", updateMap(br.recipeOne(bdh),origSize));
			outMap.put("2", updateMap(br.recipeTwo(bdh),origSize));
			outMap.put("3", updateMap(br.recipeThree(bdh),origSize));
			outMap.put("5", updateMap(br.recipeFivePLUS(bdh),origSize));
//			outMap.put("3", updateMap(br.recipeDISTS(bdh),origSize));
//			 Recipe seven is just the original file (gzipped)
			outMap.put("4", updateMap(br.recipeFive(bdh),origSize));
			// Recipe eight is just the original file (ungzipped)
			outMap.put("6", updateMap(br.recipeEight(bdh),origSize));
			// Recipe nine and ten are just as column data respectively			
			outMap.put("7", updateMap(br.recipeNine(bdh),origSize));
			outMap.put("8", updateMap(br.recipeTen(bdh),origSize));
			outMap.put("9", updateMap(br.recipeEleven(bdh),origSize));
			outMap.put("91", updateMap(br.recipeTwelve(bdh),origSize));
			outMap.put("92", updateMap(br.recipeThirteen(bdh),origSize));
			// Now return the map
			return outMap;
		}
		
		private Map<String, Double> updateMap(Map<String, Double> myMap, Double origSize) {
			// 
			for (String myKey:myMap.keySet()){
				myMap.put(myKey, origSize/myMap.get(myKey));
			}
			return myMap;
		}

		private Double getOrigFileSize(String pdbCode){
			CheckFileSize cfs = new CheckFileSize();
			return cfs.getFileSize(getPDBFilePath(pdbCode));
		}
		
		private String getPDBFilePath(String pdbCode){
			String basePath = "/Users/anthony/PDB_CACHE/data/structures/divided/mmCIF/";
			basePath +=pdbCode.charAt(1);
			basePath +=pdbCode.charAt(2);
			// Send this to lower case
			basePath +="/"+pdbCode.toLowerCase()+".cif.gz";
			return basePath;
		}
}
