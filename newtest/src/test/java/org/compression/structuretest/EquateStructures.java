package org.compression.structuretest;

import java.io.IOException;
import java.util.List;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureImpl;


public class EquateStructures {
	// A series of functions that take a HashMap and equate it to the original strtucture - for testing purposes mostly
	
	public class SimpleTest extends EquateStructuresTemp {

		public boolean structuresAreSame(Structure struct_one, Structure struct_two) throws IOException, StructureException {
			// SET THE HEADER INFORMATION
			setHeaderInfo(struct_one);
			setHeaderInfo(struct_two);
			// Function to test if the structures seen are the same
			return struct_one.toString().equals(struct_two.toString());	
		}
	}
	
	public class AtomsTest extends EquateStructuresTemp {
		
		public boolean structuresAreSame(Structure struct_one, Structure struct_two) {
			// Function to test if the structures seen are the same
			List<Atom> structOneAtoms = getAtoms(struct_one);
			List<Atom> structTwoAtoms = getAtoms(struct_two);
			// Check if the arrays are even equal
			if(structOneAtoms.size()!=structTwoAtoms.size()){
				System.out.println("SIZE OF ARRAY NOT EQUAL");
				return false;
			}
			double sum = 0.0;
			for (int i=0; i<structOneAtoms.size(); i++){
				Atom structOneAt = structOneAtoms.get(i); 
				Atom structTwoAt = structTwoAtoms.get(i);
				sum += Math.abs(structOneAt.getX()-structTwoAt.getX());
				sum += Math.abs(structOneAt.getY()-structTwoAt.getY());
				sum += Math.abs(structOneAt.getZ()-structTwoAt.getZ());
				sum += Math.abs(structOneAt.getTempFactor()-structTwoAt.getTempFactor());
				sum += Math.abs(structOneAt.getOccupancy()-structTwoAt.getOccupancy());				
			}
			System.out.println("ERROR IN VALUES: "+sum);
			if(sum==0.0){
				return true;
			}
			else{
				return false;
			}
			
			
		}
	}

	public class TypeTest extends EquateStructuresTemp {
		

		public boolean structuresAreSame(Structure struct_one, Structure struct_two)  {
			// Similar to the atom test - but here we check the atom and residue information
			List<Atom> structOneAtoms = getAtoms(struct_one);
			List<Atom> structTwoAtoms = getAtoms(struct_two);
			// Check if the arrays are even equal
			if(structOneAtoms.size()!=structTwoAtoms.size()){
				System.out.println("SIZE OF ARRAY NOT EQUAL");
				return false;
			}
			double sum = 0.0;
			for (int i=0; i<structOneAtoms.size(); i++){
				Atom structOneAt = structOneAtoms.get(i); 
				Atom structTwoAt = structTwoAtoms.get(i);
				if(structOneAt.getName().equals(structTwoAt.getName())!=true){
					System.out.println("DIFFERENCE IN ATOM NAME: "+structOneAt.getName()+" vs "+structTwoAt.getName());
					return false;
				}
			}
			return true;
			
		}
	}

	public List<Atom> getAtoms(Structure structOne) {
		// TODO Auto-generated method stub
		List<Atom> outArray = null;
 		for (Chain c: structOne.getChains()){
			for (Group g: c.getAtomGroups()){
				if (outArray==null){
					outArray = g.getAtoms();
				}
				else{
					for(Atom a: g.getAtoms()){
						outArray.add(a);
					}
				
				}

			}
		}
 		return outArray;
 		
	}
	public boolean fullStructureTest(Structure struct_one, Structure struct_two) throws IOException, StructureException{
		boolean outVar = true;
		System.out.println("IN THE TEST");
		EquateStructures es = new EquateStructures();
		System.out.println("IN THE TEST TWO");
		SimpleTest st = es.new SimpleTest();
		System.out.println("IN THE TEST THREE");
		outVar = outVar&&st.structuresAreSame(struct_one, struct_two);
		System.out.println("SIMPLE TEST "+st.structuresAreSame(struct_one, struct_two));
		AtomsTest at = es.new AtomsTest();
		outVar = outVar&&at.structuresAreSame(struct_one, struct_two);
		System.out.println("ATOM TEST "+at.structuresAreSame(struct_one, struct_two));
		TypeTest tt = es.new TypeTest();
		outVar = outVar&&tt.structuresAreSame(struct_one, struct_two);
		System.out.println("TYPE TEST "+tt.structuresAreSame(struct_one, struct_two));
		return outVar;
	}
}