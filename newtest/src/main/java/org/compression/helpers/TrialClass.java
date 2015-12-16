package org.compression.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import org.biojava.nbio.structure.StructureException;
import org.compression.biocompressor.BioCompressor;
import org.compression.biocompressor.CompressDoubles;
import org.compression.biocompressor.CompressOrderAtoms;
import org.compression.biocompressor.CompressResidues;
import org.compression.domstructureholders.BioBean;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.domstructureholders.CoreSingleStructure;
import org.compression.domstructureholders.OrderedDataStruct;
import org.compression.filecompressors.SquashBenchmark;
import org.compression.intarraycompressors.AddMinVal;
import org.compression.intarraycompressors.FindDeltas;
import org.compression.intarraycompressors.IntArrayCompressor;
import org.compression.intarraycompressors.NMRCompressor;
import org.compression.intarraycompressors.PFORCompress;
import org.compression.intarraycompressors.RunLengthEncode;
import org.compression.stringarraycompressors.RunLengthEncodeString;
import org.compression.stringarraycompressors.StringArrayCompressor;
import org.msgpack.jackson.dataformat.MessagePackFactory;
//import org.scijava.nativelib.NativeLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
//
//import de.bitkings.jbrotli.Brotli;
//import de.bitkings.jbrotli.BrotliCompressor;
//import de.bitkings.jbrotli.BrotliDeCompressor;

public class TrialClass {

	
	private BioCompressor cd = new CompressDoubles();
	private BioCompressor newOrderAtoms = new CompressOrderAtoms();
	private IntArrayCompressor intArrComp = new FindDeltas();
	private IntArrayCompressor intArrCompFour = new RunLengthEncode();
	private SquashBenchmark sb = new SquashBenchmark();
	
	private byte[] getMessagePack(BioBean bioBean) throws JsonProcessingException{
		com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper(new MessagePackFactory());
		byte[] inBuf = objectMapper.writeValueAsBytes(bioBean);
		return inBuf;
	}
	
	private byte[] getCustomBinary(BioBean bioBean) throws JsonProcessingException{
		// FUNCTION TO RETURN A CUSTOM BINARY FIELD
		
		
		
		return null;
	}
	
	public byte[] compPDB(String pdbCode) throws IOException, StructureException, IllegalAccessException, InvocationTargetException{
		OrderedDataStruct bdh = compressStruct(new BioDataStruct(pdbCode));
		BioBean bioBean = bdh.findDataAsBean();
		// Now package as a MPF
		byte[] inBuf = getMessagePack(bioBean);
		System.out.println("input length: "+ inBuf.length);
		return gzipCompress(inBuf);
	}
	
    public byte[] gzipCompress(byte[] content){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(content);
            gzipOutputStream.close();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        System.out.printf("Compression %f\n", (1.0f * content.length/byteArrayOutputStream.size()));
        return byteArrayOutputStream.toByteArray();
    }
    
    
    private OrderedDataStruct compressStruct(BioDataStruct bdh) throws IllegalAccessException, InvocationTargetException{

	CoreSingleStructure outStruct = cd.compresStructure(bdh);
	OrderedDataStruct inStruct = (OrderedDataStruct) newOrderAtoms.compresStructure(outStruct);
	ArrayList<Integer> cartnX = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_xInt();
	ArrayList<Integer> cartnY = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_yInt();
	ArrayList<Integer> cartnZ = (ArrayList<Integer>) inStruct.get_atom_site_Cartn_zInt();

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
//		// Now the occupancy and BFACTOR -> VERY SMALL GAIN
		inStruct.set_atom_site_B_iso_or_equivInt(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt()));
//		inStruct.set_atom_site_B_iso_or_equivInt(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_B_iso_or_equivInt()));
		// SMALL GAIN
		inStruct.set_atom_site_occupancyInt(intArrCompFour.compressIntArray(intArrComp.compressIntArray((ArrayList<Integer>) inStruct.get_atom_site_occupancyInt())));
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
	return inStruct;
}

}

