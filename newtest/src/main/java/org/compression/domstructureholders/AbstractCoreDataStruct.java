package org.compression.domstructureholders;

public class AbstractCoreDataStruct extends CoreDataStruct{
	
	public String getStructureCode() {
		// TODO Auto-generated method stub
		return this.getPdbCode();
	}
	public void setStructureCode(String my_code) {
		this.setPdbCode(my_code);
		
	}
}
