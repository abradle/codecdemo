//package org.compression.structureholders;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.biojava.nbio.structure.AminoAcidImpl;
//import org.biojava.nbio.structure.Atom;
//import org.biojava.nbio.structure.AtomImpl;
//import org.biojava.nbio.structure.Chain;
//import org.biojava.nbio.structure.ChainImpl;
//import org.biojava.nbio.structure.Compound;
//import org.biojava.nbio.structure.DBRef;
//import org.biojava.nbio.structure.Element;
//import org.biojava.nbio.structure.Group;
//import org.biojava.nbio.structure.HetatomImpl;
//import org.biojava.nbio.structure.NucleotideImpl;
//import org.biojava.nbio.structure.PDBHeader;
//import org.biojava.nbio.structure.ResidueNumber;
//import org.biojava.nbio.structure.Structure;
//import org.biojava.nbio.structure.StructureException;
//import org.biojava.nbio.structure.StructureIO;
//import org.biojava.nbio.structure.StructureImpl;
//import org.biojava.nbio.structure.align.util.AtomCache;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jettison.json.JSONObject;
//
//public class BasicDataHolder implements CoreSingleStructure{
//	
//	// Attribute to hold the data
//	private BioDataStruct my_data = null;
//	
//	public BasicDataHolder(String in_code) throws IOException, StructureException{
//		my_data = getBioDataStructFromPDBId(in_code);
//	}
//	
//	public BasicDataHolder(Structure inStructure) throws IOException, StructureException{
//		my_data = getBioDataStructFromBioJava(inStructure);
//	}
//
//	public BasicDataHolder(BioDataStruct bioStruct) {
//		my_data = bioStruct;
//	}
//
//	public BioDataStruct getDataAsBioDataStruct() {
//		// Function to generate a HASHMAP if required - and return the HASHMAP
//		return my_data;
//	}
//
//	public Structure getDataAsBioJava() {
//		// Function to generate a Biojava structure from the available data
//		Structure my_structure = getBioJavaFromBioDS();
//		my_structure.setPDBCode(my_data.getPdbCode());
//		return my_structure;
//	}
//
//	public String getStructureCode() {
//		// Get the code
//		return my_data.getPdbCode();
//	}
//
//
//	public void setStructureCode(String in_code) {
//		my_data.setPdbCode(in_code);		
//	}
//
//	public Map<String, Object> getDataAsHashMap() {
//		// TODO Auto-generated method stub
//		ObjectMapper m = new ObjectMapper();
//		Map<String,Object> props = m.convertValue(my_data, Map.class);
//		return props;
//	}
//
//	public JSONObject getDataAsJSON() {
//		Map<String,Object> hMap = getDataAsHashMap();
//		return new JSONObject(hMap);
//	}
//
//	
//	private Structure getBioJavaFromBioDS() {
//		// Function to create a Biojava object from a BioDS
//		// create the Empty structures
//		Structure my_structure = new StructureImpl();
//		// Get the number of items in each list
//    	Chain curr_chain = null;
//    	Group curr_group = null;
//	    // Now lets loop through the atoms
//	    for (int i = 0; i < my_data.get_atom_site_Cartn_x().size(); i++) {
//	        // NOW MAKE AN ATOM
//		    	// Make a new atom
//		    	HashMap<String, Object> info_map = set_atom_props(i);
//		    	Atom new_atom = (Atom) info_map.get("ATOM");
//		    	Group new_group = (Group) info_map.get("GROUP");
//		    	Chain new_chain = (Chain) info_map.get("CHAIN");
//				// Now add t
//				// Check if it's a new chain
//		    	if (curr_chain == null){
//		    		curr_chain  = new_chain;
//		    		my_structure.addChain(curr_chain);		    		
//		    	}
//		    	else if (new_chain.getChainID() != curr_chain.getChainID()){
//		    		curr_chain  = new_chain;
//		    		my_structure.addChain(curr_chain);
//		    	}
//				// Check if it's a new group -> make it if it is
//		    	if (curr_group == null){
//		    		curr_group = new_group;
//		    		curr_chain.addGroup(curr_group);		    		
//		    	}
//		    	else if (new_group.getResidueNumber().hashCode() != curr_group.getResidueNumber().hashCode()){
//		            // Check the group
//		    		curr_group = new_group;
//		    		curr_chain.addGroup(curr_group);
//		    	}
//		    	// Add an atom to a group
//		    	curr_group.addAtom(new_atom);
//         
//            }
//		return my_structure;
//	}
//
//	private HashMap<String, Object> set_atom_props(int i) {		    
//    	// Loop through the attributes of this atom
//		Atom a = new AtomImpl();
//		Chain c = new ChainImpl();
//        // ATOM LEVEL INFORMATION
//        // This data item is a reference to item _chem_comp_atom.atom_id defined in category CHEM_COMP_ATOM which is stored in the Chemical Component Dictionary. This atom identifier uniquely identifies each atom within each chemical component.
//        String chem_name = (String) my_data.get_atom_site_label_atom_id().get(i);
//        a.setName(chem_name);
//        // Cartesian coordinate components describing the position of this atom site.
//        try {
//            a.setX(my_data.get_atom_site_Cartn_x().get(i));
//            a.setY(my_data.get_atom_site_Cartn_y().get(i));
//            a.setZ(my_data.get_atom_site_Cartn_z().get(i));
//            // Isotropic atomic displacement parameter
//            a.setTempFactor(my_data.get_atom_site_B_iso_or_equiv().get(i));
//            // The fraction of the atom present at this atom position.
//            a.setOccupancy(my_data.get_atom_site_occupancy().get(i));
//        } catch (Exception ClassCastException) {
//            a.setX(Double.parseDouble(my_data.get_atom_site_Cartn_x().get(i).toString()));
//            a.setY(Double.parseDouble(my_data.get_atom_site_Cartn_y().get(i).toString()));
//            a.setZ(Double.parseDouble(my_data.get_atom_site_Cartn_z().get(i).toString()));
//            // Isotropic atomic displacement parameter
//            a.setTempFactor(Double.parseDouble(my_data.get_atom_site_B_iso_or_equiv().get(i).toString()));
//            // The fraction of the atom present at this atom position.
//            a.setOccupancy(Double.parseDouble(my_data.get_atom_site_occupancy().get(i).toString()));
//        } 
//
//        Integer seq_num;
//        try {
//        	a.setPDBserial((Integer) my_data.get_atom_site_id().get(i));
//        	seq_num = (Integer) my_data.get_atom_site_auth_seq_id().get(i);
//        } catch (Exception ClassCastException) {
//        	a.setPDBserial(Integer.parseInt(my_data.get_atom_site_id().get(i).toString()));
//        	seq_num = Integer.parseInt(my_data.get_atom_site_auth_seq_id().get(i).toString());
//        	
//        }
//
//    	Element my_ele = Element.R;
//    	a.setElement(Element.valueOfIgnoreCase(my_data.get_atom_site_symbol().get(i)));
//    	
//        // This data item is an author defined alternative to the value of _atom_site.label_atom_id. This item holds the PDB atom name.
//        a.setName((String) my_data.get_atom_site_auth_atom_id().get(i));
//        
//        // This item is a uniquely identifies for each alternative site for this atom position.
//        a.setAltLoc(my_data.get_atom_site_label_alt_id().get(i).toString().charAt(0));
//        
//        // NOW LET'S CONSIDER GROUP LEVEL DATA
//        // This data item is a place holder for the tags used by the PDB to identify coordinate records (e.g. ATOM or HETATM).        
//        String group_type = (String) my_data.get_atom_site_group_PDB().get(i);
//        // This data item is a reference to _entity.id defined in the ENTITY category. This item is used to identify chemically distinct portions of the molecular structure (e.g. polymer chains, ligands, solvent).
////        String ent_type = (String) my_data.get_atom_site_label_entity_id().get(i);
//        Group new_g = null;
//		if (group_type.equals("ATOM".hashCode())){
//		new_g = new AminoAcidImpl();
//		}
//		else if(group_type.equals("ATOM")){
//		new_g = new NucleotideImpl();
//		}
//		else if(group_type.equals("HETATM")){
//		new_g = new HetatomImpl();
//		}
//		else{
//			System.out.println(group_type.hashCode());
//			System.out.println("ATOM".hashCode());
//
//		// Throw an exception here
//		}
//        // This data item is an author defined alternative to the value of _atom_site.label_comp_id. This item holds the PDB 3-letter-code residue names
//		try{
//			new_g.setPDBName((String) my_data.get_atom_site_auth_comp_id().get(i));
//
//		}
//		catch(Exception NullPointerException) {
//			Object me = my_data.get_atom_site_auth_comp_id().get(i);
//			@SuppressWarnings("unused")
//			Object me2 = my_data.get_atom_site_auth_comp_id().get(i);
//			
//		}
//        ResidueNumber res_num = new ResidueNumber();
//        res_num.setChainId((String) my_data.get_atom_site_asym_id().get(i));
//        if (my_data.get_atom_site_pdbx_PDB_ins_code().get(i)==null){
//        	res_num.setInsCode("?".charAt(0));
//        }
//        else{
//            res_num.setInsCode((Character) my_data.get_atom_site_pdbx_PDB_ins_code().get(i).charAt(0));        	
//        }
//
//		// This data item is an author defined alternative to the value of _atom_site.label_seq_id. This item holds the PDB residue number.
//        res_num.setSeqNum(seq_num);
//        new_g.setResidueNumber(res_num);
//        // This data item corresponds to the PDB insertion code.
//        
//        
//        //////// SOME UNUSED DATA HERE
//        // This data item is a reference to item _chem_comp.id defined in category CHEM_COMP. This item is the primary identifier for chemical components which may either be mononers in a polymeric entity or complete non-polymer entities.
//        //String res_name = (String) my_data.get_atom_site_label_comp_id().get(i);
//        // This data item is a reference to _entity_poly_seq.num defined in the ENTITY_POLY_SEQ category. This item is used to maintain the correspondence between the chemical sequence of a polymeric entity and the sequence information in the coordinate list and in may other structural categories. This identifier has no meaning for non-polymer entities.
//        //int seq_num = (Integer) my_data.get_atom_site_label_entity_poly_seq_num().get(i);
//        
//        //// NOW LET'S CONSIDER CHAIN RELATED INFORMATION
//        //  This data item is an author defined alternative to the value of _atom_site.label_asym_id. This item holds the PDB chain identifier.
//        c.setChainID((String) my_data.get_atom_site_asym_id().get(i));
//        // This data item is reference to item _struct_asym.id defined in category STRUCT_ASYM. This item identifies an instance of particular entity in the deposited coordinate set. For a structure determined by crystallographic method this corresponds to a unique identifier within the cyrstallographic asymmetric unit.
//        c.setInternalChainID((String) my_data.get_atom_site_label_asym_id().get(i));
//        // This data item identifies the model number in an ensemble of coordinate data.
//        //my_data.get_atom_site_pdbx_PDB_model_num().get(i));
//             
//        // The net integer charge assigned to this atom.
//        // Optional uncertainties assoicated with coordinate positions, occupancies and temperature factors.
//        // Cartesian coordinate components describing the position of this atom site.
//        // THIS INFO CANNOT BE ADDED TO THE COLUMN
////        my_data.get_atom_site_pdbx_formal_charge").add("?");
////        my_data.get_atom_site_Cartn_x_esd").add("?");
////        my_data.get_atom_site_Cartn_y_esd").add("?");
////        my_data.get_atom_site_Cartn_z_esd").add("?");
////        // Isotropic atomic displacement parameter
////        my_data.get_atom_site_B_iso_or_equiv_esd").add("?");
////        // The fraction of the atom present at this atom position.
////        my_data.get_atoam_site.occupancy_esd").add("?");
//        HashMap<String, Object> hashMap= new HashMap<String, Object>();
//        hashMap.put("ATOM", a);
//        hashMap.put("CHAIN", c);
//        hashMap.put("GROUP", new_g);
//		return hashMap;
//	}
//
//	public void setDataAsHashMap(HashMap<String, ArrayList> hmap) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void setDataAsBioJava(Structure in_struct) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void setDataAsJSON(JSONObject in_json) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public int getNumAtoms() {
//		// TODO Auto-generated method stub
//		return my_data.get_atom_site_Cartn_x().size();
//	}
//	
//	private BioDataStruct getBioDataStructFromPDBId(String input_id) throws IOException, StructureException {
//		AtomCache cache = new AtomCache();
//		cache.setUseMmCif(true);
//		Structure my_struct = StructureIO.getStructure(input_id);
//		return getBioDataStructFromBioJava(my_struct);
//		// and let's count how many chains are in this structure.
//	}
//	
//	private BioDataStruct getBioDataStructFromBioJava(Structure my_struct){
//		// A now let's loop over all the atom site record
//		List<Chain> chains = my_struct.getChains();
//		// Get the number of chains
//		System.out.println(" # chains: " + chains.size());
//		// Make the data strcuture
//		BioDataStruct bioStruct = new BioDataStruct();
//		bioStruct.setPdbCode(my_struct.getPDBCode());
//		// Take the atomic information and place in a Hashmap
//		for (Chain c : chains) {
//			// Get the groups
//			String chain_id = c.getChainID();
//			for (Group g : c.getAtomGroups()) {
//				// Now loop through anf get the coords
//				String res_id = g.getPDBName();
//				ResidueNumber res_num = g.getResidueNumber();
//				for (Atom a : g.getAtoms()) {
//					updateStruct(bioStruct, a, chain_id, res_id, res_num, c, g);
//				}
//
//			}
//		}
//		return bioStruct;
//	}
//	
//	private void updateStruct(BioDataStruct bioStruct, Atom a, String chain_id, String res_id,
//			ResidueNumber res_num, Chain c, Group g) {
//		// Function to update the BioDataStruct for a given 
//		Map<String, String> myMap = new HashMap<String, String>();
//		myMap.put("HETATM", "HETATM");
//		myMap.put("AMINOACID", "ATOM");
//		myMap.put("NUCELOTIED", "ATOM");
//		bioStruct.get_atom_site_id().add(a.getPDBserial());
//		// Atom symbol
//		Element ele = a.getElement();
//		bioStruct.get_atom_site_symbol().add(ele.toString());
//		// This data item is an author defined alternative to the value of
//		// _atom_site.label_asym_id. This item holds the PDB chain
//		// identifier.
//		bioStruct.get_atom_site_asym_id().add(chain_id);
//		// This data item is an author defined alternative to the value of
//		// _atom_site.label_atom_id. This item holds the PDB atom name.
//		bioStruct.get_atom_site_auth_atom_id().add(a.getName());
//		// This data item is an author defined alternative to the value of
//		// _atom_site.label_comp_id. This item holds the PDB 3-letter-code
//		// residue names
//		bioStruct.get_atom_site_auth_comp_id().add(res_id);
//		// This data item is an author defined alternative to the value of
//		// _atom_site.label_seq_id. This item holds the PDB residue number.
//		bioStruct.get_atom_site_auth_seq_id().add(res_num.getSeqNum());
//		// This data item corresponds to the PDB insertion code.
//		Character me = res_num.getInsCode();
//		if (res_num.getInsCode()==null){
//			bioStruct.get_atom_site_pdbx_PDB_ins_code().add(null);
//
//		}
//		else{
//			bioStruct.get_atom_site_pdbx_PDB_ins_code().add(me.toString());
//
//		}
//		// This data item identifies the model number in an ensemble of
//		// coordinate data.
//		bioStruct.get_atom_site_pdbx_PDB_model_num().add(1);
//		// This data item is a place holder for the tags used by the PDB to
//		// identify coordinate records (e.g. ATOM or HETATM).
//		bioStruct.get_atom_site_group_PDB().add(myMap.get(g.getType().name()));
//		// This item is a uniquely identifies for each alternative site for
//		// this atom position.
//		if (a.getAltLoc()==" ".charAt(0)){
//			bioStruct.get_atom_site_label_alt_id().add("?");
//		}
//		else{
//			bioStruct.get_atom_site_label_alt_id().add(a.getAltLoc().toString());
//
//		}
//		// This data item is reference to item _struct_asym.id defined in
//		// category STRUCT_ASYM. This item identifies an instance of
//		// particular entity in the deposited coordinate set. For a
//		// structure determined by crystallographic method this corresponds
//		// to a unique identifier within the cyrstallographic asymmetric
//		// unit.
//		bioStruct.get_atom_site_label_asym_id().add(c.getInternalChainID().toString());
//		// This data item is a reference to item _chem_comp_atom.atom_id
//		// defined in category CHEM_COMP_ATOM which is stored in the
//		// Chemical Component Dictionary. This atom identifier uniquely
//		// identifies each atom within each chemical component.
//		bioStruct.get_atom_site_label_atom_id().add(a.getName());
//		// This data item is a reference to item _chem_comp.id defined in
//		// category CHEM_COMP. This item is the primary identifier for
//		// chemical components which may either be mononers in a polymeric
//		// entity or complete non-polymer entities.
//		bioStruct.get_atom_site_label_comp_id().add(g.getPDBName());
//		// This data item is a reference to _entity.id defined in the ENTITY
//		// category. This item is used to identify chemically distinct
//		// portions of the molecular structure (e.g. polymer chains,
//		// ligands, solvent).
//		bioStruct.get_atom_site_label_entity_id().add(myMap.get(g.getType()));
//		// This data item is a reference to _entity_poly_seq.num defined in
//		// the ENTITY_POLY_SEQ category. This item is used to maintain the
//		// correspondence between the chemical sequence of a polymeric
//		// entity and the sequence information in the coordinate list and in
//		// may other structural categories. This identifier has no meaning
//		// for non-polymer entities.
//		bioStruct.get_atom_site_label_entity_poly_seq_num().add(res_num.getSeqNum());
//		// Cartesian coordinate components describing the position of this
//		// atom site.
//		bioStruct.get_atom_site_Cartn_x().add((float) a.getX());
//		bioStruct.get_atom_site_Cartn_y().add((float) a.getY());
//		bioStruct.get_atom_site_Cartn_z().add((float) a.getZ());
//		// Isotropic atomic displacement parameter
//		bioStruct.get_atom_site_B_iso_or_equiv().add((float) a.getTempFactor());
//		// The fraction of the atom present at this atom position.
//		bioStruct.get_atom_site_occupancy().add((float) a.getOccupancy());
//		// The net integer charge assigned to this atom.
//		bioStruct.get_atom_site_pdbx_formal_charge().add(null);
//		// Optional uncertainties assoicated with coordinate positions,
//		// occupancies and temperature factors.
//		// Cartesian coordinate components describing the position of this
//		// atom site.
//		bioStruct.get_atom_site_Cartn_x_esd().add(null);
//		bioStruct.get_atom_site_Cartn_y_esd().add(null);
//		bioStruct.get_atom_site_Cartn_z_esd().add(null);
//		// Isotropic atomic displacement parameter
//		bioStruct.get_atom_site_B_iso_or_equiv_esd().add(null);
//		// The fraction of the atom present at this atom position.
//		bioStruct.get_atom_site_occupancy_esd().add(null);
//	}
//
//	public void fillBioDataStruct(String key, Object part) {
//		// Function to fill a BioDataStrcut given a key -> which maps onto a field and an object (the value)
//		if (key=="_atom_site_id"){
//			my_data.get_atom_site_id().add((Integer) part);
//			}
//			else if (key=="_atom_site_symbol"){
//			my_data.get_atom_site_symbol().add((String) part);
//			}
//			else if (key=="_atom_site_asym_id"){
//			my_data.get_atom_site_asym_id().add((String) part);
//			}
//			else if (key=="_atom_site_auth_atom_id"){
//			my_data.get_atom_site_auth_atom_id().add((String) part);
//			}
//			else if (key=="_atom_site_auth_comp_id"){
//			my_data.get_atom_site_auth_comp_id().add((String) part);
//			}
//			else if (key=="_atom_site_auth_seq_id"){
//			my_data.get_atom_site_auth_seq_id().add((Integer) part);
//			}
//			else if (key=="_atom_site_pdbx_PDB_ins_code"){
//			my_data.get_atom_site_pdbx_PDB_ins_code().add((String) part);
//			}
//			else if (key=="_atom_site_pdbx_PDB_model_num"){
//			my_data.get_atom_site_pdbx_PDB_model_num().add((Integer) part);
//			}
//			else if (key=="_atom_site_group_PDB"){
//			my_data.get_atom_site_group_PDB().add((String) part);
//			}
//			else if (key=="_atom_site_label_alt_id"){
//			my_data.get_atom_site_label_alt_id().add((String) part);
//			}
//			else if (key=="_atom_site_label_asym_id"){
//			my_data.get_atom_site_label_asym_id().add((String) part);
//			}
//			else if (key=="_atom_site_label_atom_id"){
//			my_data.get_atom_site_label_atom_id().add((String) part);
//			}
//			else if (key=="_atom_site_label_comp_id"){
//			my_data.get_atom_site_label_comp_id().add((String) part);
//			}
//			else if (key=="_atom_site_label_entity_id"){
//			my_data.get_atom_site_label_entity_id().add((String) part);
//			}
//			else if (key=="_atom_site_label_entity_poly_seq_num"){
//			my_data.get_atom_site_label_entity_poly_seq_num().add((Integer) part);
//			}
//			else if (key=="_atom_site_Cartn_x"){
//			my_data.get_atom_site_Cartn_x().add((Float) part);
//			}
//			else if (key=="_atom_site_Cartn_y"){
//			my_data.get_atom_site_Cartn_y().add((Float) part);
//			}
//			else if (key=="_atom_site_Cartn_z"){
//			my_data.get_atom_site_Cartn_z().add((Float) part);
//			}
//			else if (key=="_atom_site_B_iso_or_equiv"){
//			my_data.get_atom_site_B_iso_or_equiv().add((Float) part);
//			}
//			else if (key=="_atom_site_occupancy"){
//			my_data.get_atom_site_occupancy().add((Float) part);
//			}
//			else if (key=="_atom_site_pdbx_formal_charge"){
//			my_data.get_atom_site_pdbx_formal_charge().add((Float) part);
//			}
//			else if (key=="_atom_site_Cartn_x_esd"){
//			my_data.get_atom_site_Cartn_x_esd().add((Float) part);
//			}
//			else if (key=="_atom_site_Cartn_y_esd"){
//			my_data.get_atom_site_Cartn_y_esd().add((Float) part);
//			}
//			else if (key=="_atom_site_Cartn_z_esd"){
//			my_data.get_atom_site_Cartn_z_esd().add((Float) part);
//			}
//			else if (key=="_atom_site_B_iso_or_equiv_esd"){
//			my_data.get_atom_site_B_iso_or_equiv_esd().add((Float) part);
//			}
//			else if (key=="_atom_site_occupancy_esd"){
//			my_data.get_atom_site_occupancy_esd().add((Float) part);
//			}
//	}
//
//	public NoFloatDataStruct getDataAsNoFloatStruct() {
//		//
//		return null;
//	}
//}
