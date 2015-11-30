package org.anthonytest.newtest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.codehaus.jettison.json.JSONException;
import org.compression.domstructureholders.BioDataStruct;
import org.compression.filereaders.DataReader;
import org.compression.filereaders.ReadCols;
import org.compression.filereaders.ReadJSON;
import org.compression.filereaders.ReadRows;
import org.compression.filewriters.DataWriter;
import org.compression.filewriters.WriteFileCols;
import org.compression.filewriters.WriteFileJSON;
import org.compression.filewriters.WriteFileRows;
import org.compression.structuretest.EquateStructures;
import org.junit.Test;

public class DataReadersWriters {
	private EquateStructures es = new EquateStructures();
	 	BioDataStruct bdh = null;

	
	public DataReadersWriters() throws IOException, StructureException, JSONException{
		
		bdh = new BioDataStruct("1QMZ");
	}
	
	@Test
	public void testColsStructure() throws IOException, JSONException, StructureException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println("RUNNING COL TEST");
		DataWriter dataWrite = new WriteFileCols();
		DataReader dataRead = new ReadCols();
		BioDataStruct newStructure = readWriteFiles(dataWrite, dataRead, "COLS");
		assertTrue(es.fullStructureTest(bdh.findDataAsBioJava(), newStructure.findDataAsBioJava()));
		
		
	}

	@Test
	public void testRowsStructure() throws IOException, JSONException, StructureException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println("RUNNING ROW TEST");
		DataWriter dataWrite = new WriteFileRows();
		DataReader dataRead = new ReadRows();
		BioDataStruct newStructure = readWriteFiles(dataWrite, dataRead, "ROWS");
		assertTrue(es.fullStructureTest(bdh.findDataAsBioJava(), newStructure.findDataAsBioJava()));
		
	}
	
	@Test
	public void testJsonStructure() throws IOException, JSONException, StructureException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println("RUNNING JSON TEST");
		DataWriter dataWrite = new WriteFileJSON();
		DataReader dataRead = new ReadJSON();
		BioDataStruct newStructure = readWriteFiles(dataWrite, dataRead, "JSON");
		assertTrue(es.fullStructureTest(bdh.findDataAsBioJava(), newStructure.findDataAsBioJava()));

		
	}
	
	private BioDataStruct readWriteFiles(DataWriter myDataW, DataReader myDataR, String types) throws IOException, JSONException, StructureException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		// WRITE THE FILE
		myDataW.writeFile("OUT.TEST."+types, bdh);
		BioDataStruct newStructure = (BioDataStruct) myDataR.readFile("OUT.TEST."+types);
		return newStructure;
	}
	

}
