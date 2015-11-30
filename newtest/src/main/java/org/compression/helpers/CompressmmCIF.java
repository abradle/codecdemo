package org.compression.helpers;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.parquet.it.unimi.dsi.fastutil.Hash;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.biojava.nbio.structure.*;
import org.biojava.nbio.structure.align.helper.AlignTools;
import org.biojava.nbio.structure.align.pairwise.Alignable;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.asa.GroupAsa;
import org.biojava.nbio.structure.io.StructureIOFile;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.compression.helpers.GZIPExample;
import org.compression.intarraycompressors.AddMinVal;
import org.compression.intarraycompressors.FindDeltas;
import org.compression.intarraycompressors.IntArrayCompressor;
import org.compression.intarraycompressors.PFORCompress;
import org.compression.intarraycompressors.RunLengthEncode;
import org.compression.intarraydecompressors.IntArrayDeCompressor;
import org.compression.intarraydecompressors.RemoveDeltas;
import org.compression.intarraydecompressors.RunLengthDecode;
import org.compression.stringarraycompressors.RunLengthEncodeString;
import org.compression.stringarraycompressors.StringArrayCompressor;
import org.compression.filecompressors.FileCompressor;
import org.compression.filedecompressors.Bzip2DeCompress;
import org.compression.filedecompressors.FileDeCompressor;
import org.compression.filedecompressors.GzipDeCompress;
import org.compression.filereaders.DataReader;
import org.compression.filereaders.ReadCols;
import org.compression.filewriters.DataWriter;
import org.compression.filewriters.WriteFileCols;
import org.compression.filewriters.WriteFileJSON;
import org.compression.biocompressor.BioCompressor;
import org.compression.biocompressor.CompressDoubles;
import org.compression.biocompressor.CompressOrderAtoms;
import org.compression.biocompressor.CompressResidues;
import org.compression.biodecompression.BioDeCompressor;
import org.compression.biodecompression.DeCompressDoubles;
import org.compression.biodecompression.DeCompressResidues;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.OrderedDataStruct;
import org.compression.domstructureholders.ResidueGraphDataStruct;
import org.compression.filecompressors.Bzip2Compress;
import org.compression.filecompressors.GzipCompress;
import org.compression.filecompressors.SquashBenchmark;
import org.rcsb.spark.util.SparkUtils;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;
import scala.util.parsing.json.JSON;
import org.biojava.nbio.structure.align.model.AFPChain;
/**
	 * This class finds an mmCIF file and saves it as a csv file 
	 * 
	 * @author Anthony Bradley
	 */
	public class CompressmmCIF {

		public static void main(String[] args) throws IOException, StructureException, JSONException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InterruptedException {
			
			BioDataStruct bdh = new BioDataStruct("1FFK");
			DataWriter dataWrite = new WriteFileJSON();
			DataReader dataRead = new ReadCols();
			dataWrite.writeFile("OUT.TEST.COLS", bdh);
			BioCompressor cd = new CompressDoubles();
			BioDeCompressor bcd = new DeCompressDoubles();
			BioCompressor newCd = new CompressResidues();
			BioDeCompressor newBcd = new DeCompressResidues();
			// Remove doubles
			CoreSingleStructure outStruct = cd.compresStructure(bdh);
			dataWrite.writeFile("OUT.TEST.COLS.TWO", outStruct);
			// Remove the duplicated residue info
//			ResidueGraphDataStruct inStruct = (ResidueGraphDataStruct) newCd.compresStructure(outStruct);
//			dataWrite.writeFile("OUT.TEST.COLS.THREE", inStruct);

			// TEST THIS OUT
			BioCompressor newOrderAtoms = new CompressOrderAtoms();
			OrderedDataStruct inStruct = (OrderedDataStruct) newOrderAtoms.compresStructure(outStruct);
			dataWrite.writeFile("OUT.TEST.COLS.THREE_ORDERED", inStruct);
			
//			inStruct = orderedStruct;
			
			IntArrayCompressor intArrCompTwo = new AddMinVal();
			IntArrayCompressor intArrComp = new FindDeltas();
			IntArrayCompressor intArrCompThree = new PFORCompress();
			IntArrayCompressor intArrCompFour = new RunLengthEncode();
			IntArrayDeCompressor intArrDeCompFour = new RunLengthDecode();
			IntArrayDeCompressor intArrDeComp = new RemoveDeltas();
			// Now compress the integer arrays
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
			dataWrite.writeFile("OUT.TEST.COLS.FOUR", inStruct);
			SquashBenchmark sb = new SquashBenchmark();
			Map<String, Double> myMap = sb.benchmarkCompression("OUT.TEST.COLS.FOUR");
			System.out.println(myMap);
//			   

//			BioDataStruct newStructure = (BioDataStruct) myDataR.readFile("OUT.TEST."+types);
//			BioDataStruct newStructure = readWriteFiles(dataWrite, dataRead, "COLS");
//			assertTrue(es.fullStructureTest(bdh.getDataAsBioJava(), newStructure.getDataAsBioJava()));
//			DataReaders dr = new DataReaders();
//			BasicDataHolder bdh = new BasicDataHolder("1QMZ");
//			System.out.println(bdh.getDataAsBioDataStruct().getPdbCode());
//			DataWriters dw = new DataWriters();
//
//			WriteFileRows wfr = dw.new WriteFileRows();
//			wfr.writeFile("OUT.ROWS", bdh);
//			WriteFileJSON wfj = dw.new WriteFileJSON();
//			wfj.writeFile("OUT.JSON", bdh);
//			
//
//			WriteFileCols wfc = dw.new WriteFileCols();
//			wfc.writeFile("OUT.COLS", bdh);
//			ReadCols rfc = dr.new ReadCols();
//			BasicDataHolder rfc_structure = (BasicDataHolder) rfc.readFile("OUT.COLS");
//			
//
//
//			FileCompressor bzComp = new Bzip2Compress();
//			FileDeCompressor bzDec = new Bzip2DeCompress();
//			bzComp.compressStream(wfj.writeInStream(bdh), "OUT.JSON");		
//			ReadJSON rfj = dr.new ReadJSON();
//			BasicDataHolder rfjb_structure = (BasicDataHolder) rfj.readStream(bzDec.decompressFile("OUT.JSON.bzip2"));
//			EquateStructures es = new EquateStructures();
//			es.fullStructureTest(bdh.getDataAsBioJava(), rfjb_structure.getDataAsBioJava());
//			String input_id = "1QMZ";
//			System.out.println(input_id);
//			BasicDataHolder bdh = new BasicDataHolder(input_id);
//			DataWriters dw = new DataWriters();
//			DataReaders dr = new DataReaders();
//			FileCompressors fc = new FileCompressors();
//			FileDecompressors fd = new FileDecompressors();
//			// NOW TRY A ROUNDRIP ON ALL THREE FORMATS
//			WriteFileJSON wfj = dw.new WriteFileJSON();
//			WriteFileCols wfc = dw.new WriteFileCols();
//			WriteFileRows wfr = dw.new WriteFileRows();
//			
//			// NOW THE COMPRESSIORS
//			GzipCompress gzp = fc.new GzipCompress();
//			Bzip2Compress bzp = fc.new Bzip2Compress();
//			// NOW THE DECOMPRESSORS
//			GzipDeCompress gzd = fd.new GzipDeCompress();
//			Bzip2DeCompress gzb = fd.new Bzip2DeCompress(); 
//					
//			
//			// DO THE WRITING
//			wfj.writeFile("OUT.JSON", bdh);
//			wfc.writeFile("OUT.COLS", bdh);
//			wfr.writeFile("OUT.ROWS", bdh);
		
//			// Sequence of Compressions
//			BasicDataHolder bdh = new BasicDataHolder(input_id);
			/////// THIS IS A DEFINED RECIPE - BIO COMES BEFORE ARRAY TOO
//			bioCompOne(bdh);
//			bioCompTwo(bdh);
//			bioCompThree(bdh);
//			arrayCompOne(bdh);
//			arrayCompTwo(bdh);
//			arrayCompThree(bdh);
//			gzp.compressStream(wfj.writeInStream(bdh), "OUT.JSON");
			/////// END OF RECIPE
//			// Now get the structure back
//			BasicDataHolder bdh_ret = (BasicDataHolder) rfj.readStream(gzb.decompressFile("OUT.JSON.bzip2"));
//			// And now decompressions
//			arrayDeCompThree(bdh_ret);
//			arrayDeCompTwo(bdh_ret);
//			arrayDeCompOne(bdh_ret);
//			bioCompThree(bdh_ret);			
//			bioCompTwo(bdh_ret);
//			bioCompOne(bdh_ret);
//			// bdh should equal bdh_ret
//			
//			// DO THE WRITING OF GZIPS
//			gzp.compressStream(wfj.writeInStream(bdh), "OUT.JSON");
//			gzp.compressStream(wfc.writeInStream(bdh), "OUT.COLS");
//			gzp.compressStream(wfr.writeInStream(bdh), "OUT.ROWS");
//			
//			// DO THE WRITING OF BZIP2
//			bzp.compressStream(wfj.writeInStream(bdh), "OUT.JSON");
//			bzp.compressStream(wfc.writeInStream(bdh), "OUT.COLS");
//			bzp.compressStream(wfr.writeInStream(bdh),"OUT.ROWS");
//					
//			// SET UP THE READERS
//			ReadJSON rfj = dr.new ReadJSON();
//			ReadRows rfr = dr.new ReadRows();
//			ReadCols rfc = dr.new ReadCols();
//			
//			BasicDataHolder rfjb_structure = (BasicDataHolder) rfj.readStream(gzb.decompressFile("OUT.JSON.bzip2"));
//			System.out.println("JSONBZ" + rfjb_structure.getStructureCode());			
//			// NOW DO THE READING OF FILES
//			BasicDataHolder rfj_structure = (BasicDataHolder) rfj.readFile("OUT.JSON");
//			System.out.println("JSON" + rfj_structure.getStructureCode());			
//			BasicDataHolder rfc_structure = (BasicDataHolder) rfc.readFile("OUT.COLS");
//			System.out.println("COLS" + rfc_structure.getStructureCode());		
//			BasicDataHolder rfr_structure = (BasicDataHolder) rfr.readFile("OUT.ROWS");
//			System.out.println("ROWS" + rfr_structure.getStructureCode());	
//			
//			
//			
//			
//			
//			//BasicDataHolder tmp_sddtruct = new BasicDataHolder(input_id);
//			System.out.println(rfj_structure.getDataAsHashMap());
//			EquateStructures es = new EquateStructures();
//			SimpleTest st = es.new SimpleTest(rfj_structure);
//			System.out.println("SIMPLE TEST "+st.structuresAreSame());
//			AtomsTest at = es.new AtomsTest(rfj_structure);
//			System.out.println("ATOM TEST "+at.structuresAreSame());
//			TypeTest tt = es.new TypeTest(rfj_structure);
//			System.out.println("TYPE TEST "+tt.structuresAreSame());			
////			// Roundtrip the structure -> e.g. check the hashmap works
//			rountrip_structure(input_id);
//			System.out.println("NOW CHECKING IF ROW WRITING WORKS");
//			// Now check that the write rows and readrows works
//			HashMap<String, ArrayList> hmap = getHmapOfmmcif(input_id);
//			writeFileRows("ME.TEST", hmap);
//			HashMap<String, ArrayList> new_hmap = readFileRows("ME.TEST");
//			// Now test this hashmap against the original id
//			System.out.println(compare_hashmap_atoms_vs_pdb(input_id, new_hmap));
//			System.out.println("NOW CHECKING IF COLUMN WRITING WORKS");
//			writeFileCols("ME.COL.TEST", hmap);
//			HashMap<String, ArrayList> col_hmap = readFileCols("ME.COL.TEST");
//			// Now test this hashmap against the original id
//			System.out.println(compare_hashmap_atoms_vs_pdb(input_id, col_hmap));
//			// NOW CHECK IF THE DELTAS WORK
//			updateDeltas(hmap);
//			writeFileCols("ME.DELTA.TEST", hmap);
//			HashMap<String, ArrayList> row_delta_hmap = readFileCols("ME.DELTA.TEST");
//			removeDeltas(row_delta_hmap);
//			// Now test this hashmap against the original id
//			// ROWS DOESN'T MAKE ANY SENSE NOW IF WE DO THIS
//			//writeFileRows("ME.DELTA_UNDONE.TEST", row_delta_hmap);
//			System.out.println(compare_hashmap_atoms_vs_pdb(input_id, row_delta_hmap));
//			
//			// NOW LET'S SEE HOW
			
			
			
//			System.out.println("NOW CHECKING IF COLUMN WRITING WORKS");
//			writeFileCols("ME.COL.TEST", hmap);
//			HashMap<String, ArrayList> col_delta_hmap = readFileCols("ME.COL.TEST");
//			System.out.println(compare_hashmap_atoms_vs_pdb(input_id, col_delta_hmap));
			// THEN DO THE GZIP COMPRESSION / DECOMPRESSION
			
			// FINALLY DO THE PARQUET VERSIONS
			
			// TURN INTO TESTS AND CLEAN UP THE CLASSES
			
			// ADD MORE TESTS IN THE COMPARISON -> MAKE A TEST TO COMPARE TWO BIOJAVA STRUCTURES FOR EQUIVALENCE -> REPORT ON LEVEL OF EQUIEVALENCE
			
		}
	}		
//		private static void removeDeltas(HashMap<String, ArrayList> hmap) {
//		    // GET THE JSON AND ENCODE DELTAS    
//		    // For the cartesian coords
//		    final String[] VALUES = new String[] {"_atom_site.Cartn_x","_atom_site.Cartn_y","_atom_site.Cartn_z"};
//		    final String[] VALUES11 = new String[] {"_atom_site.id", "_atom_site.auth_seq_id", "_atom_site.label_entity_poly_seq_num"};
//		    final String[] VALUES1 = new String[] {"_atom_site.B_iso_or_equiv","_atom_site.occupancy"};
//		    
//		    final String[] VALUES_TOT = new String[] {"_atom_site.Cartn_x","_atom_site.Cartn_y","_atom_site.Cartn_z",
//		    		"_atom_site.id", "_atom_site.auth_seq_id", "_atom_site.label_entity_poly_seq_num",
//		    		"_atom_site.B_iso_or_equiv","_atom_site.occupancy"};
//		    // First transfer the columns to ints
//		    convertToInt(hmap, VALUES_TOT);
//		    // Now alter the Cartesian coords
//		    revDeltaDoubles(VALUES, hmap, 1000.0);
//		    // FOR B-factors and occupancies
//		    revDeltaDoubles(VALUES1, hmap, 100.0);
//		    // For integer arrays
//		    revDeltaInts(VALUES11, hmap);
//		}
//
//		private static void convertToInt(HashMap<String, ArrayList> hmap, String[] VALUES_TOT) {
//			// Function to convert Strings to Ints in set lists
//			for(String k: VALUES_TOT){
//				ArrayList<Integer> new_array = new ArrayList<Integer>();
//				ArrayList<String> old_array = hmap.get(k);
//				for(String s: old_array){
//					try{
//						new_array.add(Integer.parseInt(s));
//					}
//					catch (Exception NumberFormatException) {
//						return;
//					}
//					
//				}
//				hmap.put(k, new_array);
//			}
//			
//			
//		}
//
//		/**
//		 * Parses PDB metadata and writes results as a Spark DataFrame in the Parquet file format.
//		 * @param fileName Parquet file
//		 * @throws IOException 
//		 * @throws StructureException 
//		 */
//		public void convertmmCIFToCompressed(String input_id, HashMap<String, ArrayList<Double>> results_map) throws IOException, StructureException {
//			CompressAndCountFiles comp = new CompressAndCountFiles();
//		    //System.out.println(inputFileName + " -> " + outputFileName);
//			// setup Spark and Spark SQL
//			//JavaSparkContext sc = new JavaSparkContext();
//			JavaSparkContext sc = SparkUtils.getJavaSparkContext();
//		    SQLContext sql = SparkUtils.getSqlContext(sc);
//		    // Put this list in 
//		    results_map.put(input_id, new ArrayList<Double>());
//		    // Now lets get a structure and turn it into a hashmap
//		    HashMap<String, ArrayList> hmap = getHmapOfmmcif(input_id);        
//            // Now we generate the relevant files for comparison for this PDB entry
//	        JSONObject myjson = new JSONObject(hmap);
//	        // Set the paths for the different file types
//	        String base_path = "/Users/anthony/Documents/TRIAL_DIR/"+input_id+"/";
//	        // Make sure the directory is built
//	        new File(base_path).mkdirs();
//	        // Now set these files
//	        String json_path = base_path+input_id+".json";
//	        String parquet_file = base_path+input_id+"_parquet_file";
//	        String mmCIF_rows = base_path+input_id+"_rows.mmcif";
//	        String mmCIF_columns = base_path+input_id+"_cols.mmcif";
//	        String mmCIF_columns_delta = base_path+input_id+"_cols_delta.mmcif";
//	        String parquet_file_delta = base_path+input_id+"_parquet_file_delta";
//	        String json_file_delta = base_path+input_id+"delta.json";
//	        // First write the JSON
//	        FileWriter file = new FileWriter(json_path);
//			file.write(myjson.toString());
//			System.out.println("Successfully Copied JSON Object to File...");
//	        file.flush();
//	        file.close();
//	        // Second write the simple parquet
//	        DataFrame df = sql.jsonFile(json_path);
//	        df.show();
//	        df.write().format("parquet").mode(SaveMode.Overwrite).save(parquet_file);    
//		    System.out.println("Succesfully written JSON to partquet");
//		    // Third write the mmCIF as rows
//		    writeFileRows(mmCIF_rows, hmap);
//			// Fourth we write the mmCIF as a columns
//			writeFileCols(mmCIF_columns, hmap);
//		    // Fifth we write the columns with DELTAS
//			updateDeltas(hmap);
//		    // Now we write the deltas
//		    writeFileCols(mmCIF_columns_delta, hmap);	    
//		    // Sixth we write the PARQUET with DELTAS
//	        // First write the JSON
//		    JSONObject my_delta_json = new JSONObject(hmap);
//	        FileWriter jd_file = new FileWriter(json_file_delta);
//	        jd_file.write(my_delta_json.toString());
//			System.out.println("Successfully Copied JSON Object to File...");
//			jd_file.flush();
//			jd_file.close();
//	        // Second write the simple parquet
//	        DataFrame df_1 = sql.jsonFile(json_file_delta);
//	        df_1.show();
//	        df_1.write().format("parquet").mode(SaveMode.Overwrite).save(parquet_file_delta);    
//		    System.out.println("Succesfully written JSON to partquet");
//		    sc.close();
//		    //////// GET THE FILE SIZES (AND DIR SIZES) AND WRITE THEM TO A SEPERATE FILE
//	        // Add the size of the flat files
//	        results_map.get(input_id).add(comp.getFileSize(json_path));
//	        results_map.get(input_id).add(comp.getFileSize(mmCIF_rows));
//	        results_map.get(input_id).add(comp.getFileSize(mmCIF_columns));
//	        results_map.get(input_id).add(comp.getFileSize(mmCIF_columns_delta));
//	        results_map.get(input_id).add(comp.getFileSize(json_file_delta));
//	        // Add the sizes of the gzip files
//		    results_map.get(input_id).add(comp.compressGzipFile(json_path));
//		    results_map.get(input_id).add(comp.compressGzipFile(mmCIF_rows));
//		    results_map.get(input_id).add(comp.compressGzipFile(mmCIF_columns));
//		    results_map.get(input_id).add(comp.compressGzipFile(mmCIF_columns_delta));
//		    results_map.get(input_id).add(comp.compressGzipFile(json_file_delta));
//		    // Add the sizes of the flat parquet folders
//		    results_map.get(input_id).add(comp.getFolderSize(parquet_file));
//		    results_map.get(input_id).add(comp.getFolderSize(parquet_file_delta));    
//		    // Add the sizes of the GZIP files
//		    results_map.get(input_id).add(comp.compressGZipDirectory(parquet_file));
//		    results_map.get(input_id).add(comp.compressGZipDirectory(parquet_file_delta));
//		}
//
//		private static boolean compare_hashmap_atoms_vs_pdb(Structure old_struct, HashMap<String, ArrayList> hmap) {
//			    	// Get the structures
//	    	Structure new_struct = hmap_to_biojava_struct(hmap);	    	
//	    	// NOW WE SET THIS HEADER INFORMATION
//	        PDBHeader header = old_struct.getPDBHeader();
//	        List<DBRef> dbref = old_struct.getDBRefs();
//	        List<Compound> compund_list = old_struct.getCompounds();
//	        List<Chain> chain_list = old_struct.getChains();
//	        String pdb_id = old_struct.getPdbId();
//	    	new_struct.setPDBHeader(header);
//	    	new_struct.setDBRefs(dbref);
//	    	new_struct.setCompounds(compund_list);
//	    	new_struct.setChains(chain_list);
//	    	new_struct.setPDBCode(pdb_id);
//		//	    	System.out.println(old_struct.toString());
//		//	    	System.out.println(new_struct.toString());
//			    	return old_struct.toString().equals(new_struct.toString());	
//					
//				}
//
//
//
//
//		private static void updateDeltas(HashMap<String, ArrayList> hmap) {
//		    // GET THE JSON AND ENCODE DELTAS    
//		    // For the cartesian coords
//		    final String[] VALUES = new String[] {"_atom_site.Cartn_x","_atom_site.Cartn_y","_atom_site.Cartn_z"};
//		    deltaDoubles(VALUES, hmap, 1000.0);
//		    // FOR B-factors and occupancies
//		    final String[] VALUES1 = new String[] {"_atom_site.B_iso_or_equiv","_atom_site.occupancy"};
//		    deltaDoubles(VALUES1, hmap, 100.0);
//		    // For integer arrays
//		    final String[] VALUES11 = new String[] {"_atom_site.id", "_atom_site.auth_seq_id", "_atom_site.label_entity_poly_seq_num"};
//		    deltaInts(VALUES11, hmap);
//			
//		}
//
//
//s
//
//	  
//
//		private static void deltaDoubles(String[] VALUES, HashMap<String, ArrayList> hmap, double multiplier) { 
//	    List<String> tmp = new ArrayList<String>(Arrays.asList(VALUES));
//	    for (String key : hmap.keySet()) {
//	    	if(tmp.contains(key)){
//	    		// Get the original array
//	    		ArrayList<Double> my_array = hmap.get(key);
//	    		// Get the integer array we'll be using 
//	    		ArrayList<Integer> new_array = getDeltaDoubleArray(my_array, multiplier);
//	    		//ArrayList<Integer> newer_array = compressIntArray(new_array, key);
//	    		// Now make all values positive
//	    		ArrayList<Integer> new_non_neg_array = addMinVal(new_array);
//	    		ArrayList<Integer> new_non_neg_array_comp = compressIntArray(new_non_neg_array, key+"_NON_NEG");
//	    		// Now test with sorted
////	    		new_array.sort(null);
////	    		compressIntArray(new_array, key+"_SORTED");
////	    		ArrayList<Integer> new_non_neg_sort_array = addMinVal(new_array);
////	    		compressIntArray(new_non_neg_sort_array, key+"_SORTED_NON_NEG");
//	    		System.out.println(new_non_neg_array_comp);
//			    hmap.put(key, new_non_neg_array_comp);
//	    	}
//	    }
//	    }
//
//		
//
//
//
//		
//
//
//	    
//
//	    
//	    
//
//	    
//
//
//
//	    
//	    @SuppressWarnings("unchecked")
//		
//	
//	    @SuppressWarnings({ "unchecked", "static-access" })
//		private static void rountrip_structure(String input_id) throws IOException, StructureException{
//	    	// Function to convert an MMCif to a HASHMAP and back again
//	    	// First let's get the original structure
//	    	Structure structure = getmmCif_pdb_id(input_id);
//	    	// Get the hashmap
//	    	HashMap<String, ArrayList> hmap = getHmapOfmmcif(input_id);
//	    	// Now compare
//	    	Boolean comparison = compare_hashmap_atoms_vs_pdb(structure, hmap);
//	    	// Now print the comparison
//	    	System.out.println(comparison);
//
//	    }
//
//		private static Structure getmmCif_pdb_id (String input_id) throws IOException, StructureException{
//	    	// Function to get an MMCIF from a PDB ID
//	    	// Set the read
//			AtomCache cache = new AtomCache();
//	        cache.setUseMmCif(true);
//	        Structure struct = StructureIO.getStructure(input_id);
//	        return struct;
//	    }
//		
//	    private static boolean compare_hashmap_atoms_vs_pdb(String input_id, HashMap<String, ArrayList> hmap) throws IOException, StructureException{
//	    	// Get the original structure
//	    	Structure old_struct = getmmCif_pdb_id(input_id);
//	    	// Get the structures
//	    	Structure new_struct = hmap_to_biojava_struct(hmap);	    	
//	    	// NOW WE SET THIS HEADER INFORMATION
//	        PDBHeader header = old_struct.getPDBHeader();
//	        List<DBRef> dbref = old_struct.getDBRefs();
//	        List<Compound> compund_list = old_struct.getCompounds();
//	        List<Chain> chain_list = old_struct.getChains();
//	        String pdb_id = old_struct.getPdbId();
//	    	new_struct.setPDBHeader(header);
//	    	new_struct.setDBRefs(dbref);
//	    	new_struct.setCompounds(compund_list);
//	    	new_struct.setChains(chain_list);
//	    	new_struct.setPDBCode(pdb_id);
////	    	System.out.println(old_struct.toString());
////	    	System.out.println(new_struct.toString());
//	    	return old_struct.toString().equals(new_struct.toString());    	
//	    }
//	    
//	    



	
