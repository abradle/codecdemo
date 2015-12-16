package org.compression.methodbenchmarking;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import org.biojava.nbio.structure.StructureException;
import org.codehaus.jettison.json.JSONException;
import org.compression.biocompressor.BioCompressor;
import org.compression.biocompressor.CompressDists;
import org.compression.biocompressor.CompressDoubles;
import org.compression.biocompressor.CompressOrderAtoms;
import org.compression.biocompressor.CompressResidues;
import org.compression.biodecompression.BioDeCompressor;
import org.compression.biodecompression.DeCompressDoubles;
import org.compression.biodecompression.DeCompressResidues;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.NoFloatDataStruct;
import org.compression.domstructureholders.OrderedDataStruct;
import org.compression.domstructureholders.ResidueGraphDataStruct;
import org.compression.filecompressors.SquashBenchmark;
import org.compression.filereaders.DataReader;
import org.compression.filereaders.ReadCols;
import org.compression.filesize.CheckFileSize;
import org.compression.filewriters.DataWriter;
import org.compression.filewriters.WriteFileCols;
import org.compression.filewriters.WriteFileJSON;
import org.compression.filewriters.WriteFileRows;
import org.compression.intarraycompressors.AddMinVal;
import org.compression.intarraycompressors.FindDeltas;
import org.compression.intarraycompressors.IntArrayCompressor;
import org.compression.intarraycompressors.NMRCompressor;
import org.compression.intarraycompressors.PFORCompress;
import org.compression.intarraycompressors.RunLengthEncode;
import org.compression.stringarraycompressors.RunLengthEncodeString;
import org.compression.stringarraycompressors.StringArrayCompressor;

public class BenchmarkRecipes {
	
	private DataWriter dataWrite = new WriteFileJSON();
	private DataWriter rowWrite = new WriteFileRows();
	private DataWriter colWrite = new WriteFileCols();

	private BioCompressor cd = new CompressDoubles();
	private BioCompressor newCd = new CompressResidues();
	private BioCompressor newOrderAtoms = new CompressOrderAtoms();
	private IntArrayCompressor intArrCompTwo = new AddMinVal();
	private IntArrayCompressor intArrComp = new FindDeltas();
	private IntArrayCompressor intArrCompThree = new PFORCompress();
	private IntArrayCompressor intArrCompFour = new RunLengthEncode();
	private SquashBenchmark sb = new SquashBenchmark();
	
	public Map<String, Double> recipeOne(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		String fileName = bdh.getPdbCode()+"RECIPEONE";
		dataWrite.writeFile(fileName, bdh);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		return myMap;
	}
	
	public Map<String, Double> recipeTwo(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to Integers
		String fileName = bdh.getPdbCode()+"RECIPETWO";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		dataWrite.writeFile(fileName, outStruct);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		return myMap;

	}	
	
	public Map<String, Double> recipeThree(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to Integers and then rorder atoms AND structure as graph
		String fileName = bdh.getPdbCode()+"RECIPETHREE";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		OrderedDataStruct inStruct = (OrderedDataStruct) newOrderAtoms.compresStructure(outStruct);
		
		ArrayList<Integer> cartnX = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_xInt();
		ArrayList<Integer> cartnY = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_yInt();
		ArrayList<Integer> cartnZ = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_zInt();
		inStruct.set_atom_site_Cartn_xInt(intArrComp.compressIntArray(cartnX));
		inStruct.set_atom_site_Cartn_yInt(intArrComp.compressIntArray(cartnY));
		inStruct.set_atom_site_Cartn_zInt(intArrComp.compressIntArray(cartnZ));		
//		// Now the occupancy and BFACTOR -> VERY SMALL GAIN
		inStruct.set_atom_site_B_iso_or_equivInt(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt()));
//		inStruct.set_atom_site_B_iso_or_equivInt(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt()));
		dataWrite.writeFile( fileName, inStruct);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression( fileName);
		return myMap;
	}	
	

	public Map<String, Double> recipeFour(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert double ti integers - reorder atoms and compress everything
		String fileName = bdh.getPdbCode()+"RECIPEFOUR";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		OrderedDataStruct inStruct = (OrderedDataStruct) newOrderAtoms.compresStructure(outStruct);
		ArrayList<Integer> cartnX = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_xInt();
		ArrayList<Integer> cartnY = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_yInt();
		ArrayList<Integer> cartnZ = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_zInt();
//		// 
//		inStruct.set_atom_site_Cartn_xInt(intArrCompThree.compressIntArray(intArrComp.compressIntArray(cartnX)));
//		inStruct.set_atom_site_Cartn_yInt(intArrCompThree.compressIntArray(intArrComp.compressIntArray(cartnY)));
//		inStruct.set_atom_site_Cartn_zInt(intArrCompThree.compressIntArray(intArrComp.compressIntArray(cartnZ)));
		///// THIS ONE MAKES BROTLI MUCH BETTER 
//		inStruct.set_atom_site_Cartn_xInt(intArrCompTwo.compressIntArray(intArrComp.compressIntArray(cartnX)));
		int numModels = inStruct.getNumModels();
		if(numModels>1){
			// NMR COMPRESSION IS DISAPPOINTING
			NMRCompressor nmrArrComp = new NMRCompressor();
			// DELTA ON ATOMS -> SMALL GAIN
			// DOING DELTAS ON MODELS -> SMALL GAIN (MAY BE BETTER WHEN BINARY)
			inStruct.set_atom_site_Cartn_xInt(intArrComp.compressIntArray(nmrArrComp.compressIntArrayNMR(cartnX, numModels)));
			inStruct.set_atom_site_Cartn_yInt(intArrComp.compressIntArray(nmrArrComp.compressIntArrayNMR(cartnY, numModels)));
			inStruct.set_atom_site_Cartn_zInt(intArrComp.compressIntArray(nmrArrComp.compressIntArrayNMR(cartnZ, numModels)));
			// WE CAN DO B-FACTOR RUN LENGTH ENCODING TOO
			inStruct.set_atom_site_B_iso_or_equivInt(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt())));
			inStruct.set_atom_site_occupancyInt(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_occupancyInt())));
			inStruct.set_atom_site_pdbx_PDB_model_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_pdbx_PDB_model_num())));			
		}
		else{
			inStruct.set_atom_site_Cartn_xInt(intArrComp.compressIntArray(cartnX));
			inStruct.set_atom_site_Cartn_yInt(intArrComp.compressIntArray(cartnY));
			inStruct.set_atom_site_Cartn_zInt(intArrComp.compressIntArray(cartnZ));		
//			// Now the occupancy and BFACTOR -> VERY SMALL GAIN
			inStruct.set_atom_site_B_iso_or_equivInt(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt()));
//			inStruct.set_atom_site_B_iso_or_equivInt(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt()));
			// SMALL GAIN
			inStruct.set_atom_site_occupancyInt(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_occupancyInt())));
//			
			// Now the sequential numbers - huge gain - new order of good compressors
			inStruct.set_atom_site_pdbx_PDB_model_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_pdbx_PDB_model_num())));
		}

		inStruct.set_atom_site_auth_seq_id(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_auth_seq_id())));
		inStruct.set_atom_site_label_entity_poly_seq_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_label_entity_poly_seq_num())));			
		inStruct.set_atom_site_id(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_id())));
		//// NOW THE STRINGS  - small gain
		StringArrayCompressor stringRunEncode = new RunLengthEncodeString();
		inStruct.set_atom_site_label_alt_id(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_label_alt_id()));
		//inStruct.set_atom_site_label_entity_id(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_label_entity_id()));
		inStruct.set_atom_site_pdbx_PDB_ins_code(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_pdbx_PDB_ins_code()));
		dataWrite.writeFile(fileName, inStruct);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression( fileName);
		return myMap;
	} 	
	
	
	public Map<String, Double> recipeDISTS(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to Integers and then rorder atoms AND structure as graph
		String fileName = bdh.getPdbCode()+"RECIPEDISTS";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		CompressDists cdd = new CompressDists();
		NoFloatDataStruct inStruct = (NoFloatDataStruct) cdd.compresStructure(outStruct);
		dataWrite.writeFile( fileName, inStruct);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression( fileName);
		return myMap;
	}	

	public Map<String, Double> recipeFive(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to ints, create a hash for the residue information and compress everything
		String fileName = bdh.getPdbCode()+"RECIPEFIVE";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		ResidueGraphDataStruct inStruct = (ResidueGraphDataStruct) newCd.compresStructure(outStruct);
		ArrayList<Integer> cartnX = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_xInt();
		ArrayList<Integer> cartnY = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_yInt();
		ArrayList<Integer> cartnZ = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_zInt();
		inStruct.set_atom_site_Cartn_xInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray(cartnX)));
		inStruct.set_atom_site_Cartn_yInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray(cartnY)));
		inStruct.set_atom_site_Cartn_zInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray(cartnZ)));

		// Now the occupancy and BFACTOR
		inStruct.set_atom_site_B_iso_or_equivInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt())));
//		inStruct.set_atom_site_occupancyInt(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_occupancyInt())));
//		
//		// Now the sequential numbers
//		inStruct.set_atom_site_pdbx_PDB_model_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_pdbx_PDB_model_num())));
//		inStruct.set_atom_site_auth_seq_id(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_auth_seq_id())));
//		inStruct.set_atom_site_label_entity_poly_seq_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_label_entity_poly_seq_num())));			
//		inStruct.set_atom_site_id(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_id())));
//		// NOW THE STRINGS
//		StringArrayCompressor stringRunEncode = new RunLengthEncodeString();
//		inStruct.set_atom_site_label_alt_id(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_label_alt_id()));
//		inStruct.set_atom_site_label_entity_id(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_label_entity_id()));
//		inStruct.set_atom_site_pdbx_PDB_ins_code(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_pdbx_PDB_ins_code()));
		dataWrite.writeFile(fileName, inStruct);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		System.out.println(myMap);
		return myMap;	
	}
	public Map<String, Double> recipeFivePLUS(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to ints, create a hash for the residue information and compress everything
		String fileName = bdh.getPdbCode()+"RECIPEFIVEPLUS";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		ResidueGraphDataStruct inStruct = (ResidueGraphDataStruct) newCd.compresStructure(outStruct);
		ArrayList<Integer> cartnX = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_xInt();
		ArrayList<Integer> cartnY = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_yInt();
		ArrayList<Integer> cartnZ = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_zInt();
		inStruct.set_atom_site_Cartn_xInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray(cartnX)));
		inStruct.set_atom_site_Cartn_yInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray(cartnY)));
		inStruct.set_atom_site_Cartn_zInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray(cartnZ)));

		// Now the occupancy and BFACTOR
		inStruct.set_atom_site_B_iso_or_equivInt(intArrCompThree.compressIntArray(intArrCompTwo.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt())));
		inStruct.set_atom_site_occupancyInt(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_occupancyInt())));
		
		// Now the sequential numbers
		inStruct.set_atom_site_pdbx_PDB_model_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_pdbx_PDB_model_num())));
		inStruct.set_atom_site_auth_seq_id(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_auth_seq_id())));
		inStruct.set_atom_site_label_entity_poly_seq_num(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_label_entity_poly_seq_num())));			
		inStruct.set_atom_site_id(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_id())));
		// NOW THE STRINGS
		StringArrayCompressor stringRunEncode = new RunLengthEncodeString();
		inStruct.set_atom_site_label_alt_id(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_label_alt_id()));
		inStruct.set_atom_site_label_entity_id(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_label_entity_id()));
		inStruct.set_atom_site_pdbx_PDB_ins_code(stringRunEncode.compressStringArray((ArrayList<String>) inStruct.get_atom_site_pdbx_PDB_ins_code()));
		dataWrite.writeFile(fileName, inStruct);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		System.out.println(myMap);
		return myMap;	
	}
		
//	public Map<String, Double> recipeSix(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
//		// Convert doubles to ints, create a hash for the residue information
//		String fileName = bdh.getPdbCode()+"RECIPESIX";
//		CoreSingleStructure outStruct = cd.compresStructure(bdh);
//		ResidueGraphDataStruct inStruct = (ResidueGraphDataStruct) newCd.compresStructure(outStruct);
//		dataWrite.writeFile(fileName, inStruct);
//		// Now do the Squash benchmark
//		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
//		System.out.println(myMap);
//		return myMap;
//	}
	
	public Map<String, Double> recipeSeven(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Just the MMCIf file - uncompress and compress
		String fileName = getPDBFilePath(bdh.getPdbCode());
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		return myMap;
	}

	
	public Map<String, Double> recipeEight(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Just the MMCIf file - uncompress and compress
		String fileName = getPDBFilePath(bdh.getPdbCode());
		String newFileName = bdh.getPdbCode()+".mmcif";
		gunzipIt(fileName, newFileName);
		// Now uncompress this file
		Map<String, Double> myMap = sb.benchmarkCompression(newFileName);
		return myMap;
	}

	
	public Map<String, Double> recipeEleven(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Write out a binary file
		WriteFileCols dataWrite = new WriteFileCols();
		String fileName = bdh.getPdbCode()+"RECIPEELEVEN";
		dataWrite.writeBinaryFile(bdh, fileName);
		SquashBenchmark sb = new SquashBenchmark();
		Map<String, Double> me = sb.benchmarkCompression(fileName);
		return me;
	}

	public Map<String, Double> recipeTwelve(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to Integers
		WriteFileCols dataWrite = new WriteFileCols();
		String fileName = bdh.getPdbCode()+"RECIPETWELVE";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		dataWrite.writeBinaryFile(outStruct, fileName);
		SquashBenchmark sb = new SquashBenchmark();
		Map<String, Double> me = sb.benchmarkCompression(fileName);
		return me;
	}

	public Map<String, Double> recipeThirteen(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Convert doubles to Integers
		WriteFileCols dataWrite = new WriteFileCols();
		String fileName = bdh.getPdbCode()+"RECIPETHIRTEEN";
		CoreSingleStructure outStruct = cd.compresStructure(bdh);
		dataWrite.writeFile(fileName, outStruct);
		SquashBenchmark sb = new SquashBenchmark();
		Map<String, Double> me = sb.benchmarkCompression(fileName);
		return me;
	}
	
	public Map<String, Double> recipeNine(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Write file as rows instead
		String fileName = bdh.getPdbCode()+"RECIPENINE";
		rowWrite.writeFile(fileName, bdh);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		return myMap;
			
		}
	
	public Map<String, Double> recipeTen(BioDataStruct bdh) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, JSONException, StructureException, InterruptedException{
		// Write file as rows instead
		String fileName = bdh.getPdbCode()+"RECIPETEN";
		colWrite.writeFile(fileName, bdh);
		// Now do the Squash benchmark
		Map<String, Double> myMap = sb.benchmarkCompression(fileName);
		return myMap;
		}	
	private String getPDBFilePath(String pdbCode){
		String basePath = "/Users/anthony/PDB_CACHE/data/structures/divided/mmCIF/";
		basePath +=pdbCode.charAt(1);
		basePath +=pdbCode.charAt(2);
		// Send this to lower case
		basePath +="/"+pdbCode.toLowerCase()+".cif.gz";
		return basePath;
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
