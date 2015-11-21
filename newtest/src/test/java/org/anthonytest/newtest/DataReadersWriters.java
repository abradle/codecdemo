package org.anthonytest.newtest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.codehaus.jettison.json.JSONException;
import org.compression.filereaders.DataReader;
import org.compression.filereaders.ReadCols;
import org.compression.filereaders.ReadJSON;
import org.compression.filereaders.ReadRows;
import org.compression.filewriters.DataWriter;
import org.compression.filewriters.WriteFileCols;
import org.compression.filewriters.WriteFileJSON;
import org.compression.filewriters.WriteFileRows;
import org.compression.structureholders.BasicDataHolder;
import org.compression.structuretest.EquateStructures;
import org.junit.Test;

public class DataReadersWriters {
	private BasicDataHolder bdh = null;
	private EquateStructures es = new EquateStructures();
	
//	public void main(String[] args) throws IOException, StructureException, JSONException {
//		testJsonStructure();
//	}
	
	public DataReadersWriters() throws IOException, StructureException, JSONException{
		bdh = new BasicDataHolder("1QMZ");
	}
	
	@Test
	public void testColsStructure() throws IOException, JSONException, StructureException {
		System.out.println("RUNNING COL TEST");
		DataWriter dataWrite = new WriteFileCols();
		DataReader dataRead = new ReadCols();
		BasicDataHolder newStructure = readWriteFiles(dataWrite, dataRead, "COLS");
		assertTrue(es.fullStructureTest(bdh.getDataAsBioJava(), newStructure.getDataAsBioJava()));
		
		
	}

	@Test
	public void testRowsStructure() throws IOException, JSONException, StructureException {
		System.out.println("RUNNING ROW TEST");
		DataWriter dataWrite = new WriteFileRows();
		DataReader dataRead = new ReadRows();
		BasicDataHolder newStructure = readWriteFiles(dataWrite, dataRead, "ROWS");
		assertTrue(es.fullStructureTest(bdh.getDataAsBioJava(), newStructure.getDataAsBioJava()));
		
	}
	
	@Test
	public void testJsonStructure() throws IOException, JSONException, StructureException {
		System.out.println("RUNNING JSON TEST");
		DataWriter dataWrite = new WriteFileJSON();
		DataReader dataRead = new ReadJSON();
		BasicDataHolder newStructure = readWriteFiles(dataWrite, dataRead, "JSON");
		assertTrue(es.fullStructureTest(bdh.getDataAsBioJava(), newStructure.getDataAsBioJava()));

		
	}
	
	private BasicDataHolder readWriteFiles(DataWriter myDataW, DataReader myDataR, String types) throws IOException, JSONException, StructureException{
		// WRITE THE FILE
		myDataW.writeFile("OUT.TEST."+types, bdh);
		BasicDataHolder newStructure = (BasicDataHolder) myDataR.readFile("OUT.TEST."+types);
		return newStructure;
	}
	

}
