package org.compression.domstructureholders;

import java.util.ArrayList;
import java.util.List;

public class PDBGroup {
	public List<String> getAtomInfo() {
		return atomInfo;
	}
	public void setAtomInfo(List<String> atomInfo) {
		this.atomInfo = atomInfo;
	}
	public List<Integer> getBondOrders() {
		return bondOrders;
	}
	public void setBondOrders(List<Integer> bondOrders) {
		this.bondOrders = bondOrders;
	}
	public List<Integer> getBondLengths() {
		return bondLengths;
	}
	public void setBondLengths(List<Integer> bondLengths) {
		this.bondLengths = bondLengths;
	}
	public List<Integer> getBondIndices() {
		return bondIndices;
	}
	public void setBondIndices(List<Integer> bondIndices) {
		this.bondIndices = bondIndices;
	}
	private List<String> atomInfo = new ArrayList<String>();
	private List<Integer> bondOrders = new ArrayList<Integer>();
	private List<Integer> bondLengths = new ArrayList<Integer>();
	private List<Integer> bondIndices = new ArrayList<Integer>();
	
}
