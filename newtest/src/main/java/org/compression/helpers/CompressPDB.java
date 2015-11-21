package org.compression.helpers;
//package org.anthonytest.newtest;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Scanner;
//
//import org.biojava.nbio.structure.StructureException;
//
///**
//	 * This class runs the processing required - to analyse whole PDB
//	 * 
//	 * @author Anthony Bradley
//	 */
//	public class CompressPDB {
//		
//		public static void main(String[] args) throws IOException, StructureException {
//			// MAIN FUNCTION JUST ANALYSES THE WHOLE PDB
//			// Get the list of JSON from a file
//			Scanner s = new Scanner(new File("/Users/anthony/Documents/TRIAL_DIR/out.json"));
//			ArrayList<String> list = new ArrayList<String>();
//			while (s.hasNext()){
//			    list.add(s.next());
//			}
//			s.close();
//			analyse_structures(list, "OUTPUT.csv");
//
//		}
//		private static void analyse_structures(ArrayList<String> list, String output_file) throws IOException, StructureException{
//			FileWriter fw = new FileWriter(output_file);
//			fw.write("pdb_id,");
//			// Different column headers
//			fw.write("json_path,");
//			fw.write("mmCIF_rows,");
//			fw.write("mmCIF_columns,");
//			fw.write("mmCIF_columns_delta,");
//			fw.write("json_file_delta,");
//	        // Add the sizes of the gzip files
//			fw.write("json_path_gz,");
//			fw.write("mmCIF_rows_gz,");
//			fw.write("mmCIF_columns_gz,");
//			fw.write("mmCIF_columns_delta_gz,");
//			fw.write("json_file_delta_gz,");
//		    // Add the sizes of the flat parquet folders
//			fw.write("parquet_file,");
//			fw.write("parquet_file_delta,");  
//		    // Add the sizes of the GZIP files
//		    fw.write("parquet_file_gz,");
//		    fw.write("parquet_file_delta_gz\n");
//			for (int i = 0; i < list.size(); i++) {
//				// Make the downloader
//				CompressmmCIF downloader = new CompressmmCIF();
//				// Make the place where the results can go
//				HashMap<String, ArrayList<Double>> results_map = new HashMap<String, ArrayList<Double>>();
//				String pdb_id = list.get(i);
//				downloader.convertmmCIFToCompressed(pdb_id, results_map);
//				// Now write another line of this hashmap
//				fw.write(pdb_id+",");
//				// Now write out the results
//				ArrayList<Double> my_list = results_map.get(pdb_id);
//				for (int j=0; j < my_list.size(); j++){
//					// 
//					fw.write(my_list.get(j).toString());
//					if (j < my_list.size() - 1)
//					{
//						fw.write(",");
//					}
//					else{
//						fw.write("\n");
//						fw.flush();
//					}
//				}
//			}
//			// Now close this guy down
//			fw.flush();
//			fw.close();
//		}
//		
//	}