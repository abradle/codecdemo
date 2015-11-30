package org.compression.structuretest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.biojava.nbio.structure.*;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.compression.domstructureholders.CoreSingleStructure;

public class EquateStructuresTemp implements StructureTest{
	// Now lets get the two structures	

	public void setHeaderInfo(Structure struct_one) throws IOException, StructureException {
		// TODO Auto-generated method stub
		Structure struct_two = getStructFromPDB(struct_one.getPDBCode());
        PDBHeader header = struct_two.getPDBHeader();
        List<DBRef> dbref = struct_two.getDBRefs();
        List<Compound> compund_list = struct_two.getCompounds();
        List<Chain> chain_list = struct_two.getChains();
        String pdb_id = struct_two.getPdbId();
    	struct_one.setPDBHeader(header);
    	struct_one.setDBRefs(dbref);
    	struct_one.setCompounds(compund_list);
    	struct_one.setChains(chain_list);
    	struct_one.setPDBCode(pdb_id);
	}

	private Structure getStructFromPDB(String pdb_code) throws IOException, StructureException {
		//
		AtomCache cache = new AtomCache();
        cache.setUseMmCif(true);
        Structure struct = StructureIO.getStructure(pdb_code);
        return struct;
	}


	public boolean structuresAreSame(CoreSingleStructure struct_one, CoreSingleStructure struct_two) {
		// TODO Auto-generated method stub
		return false;
	}
	
			
	
}
