package org.compression.domstructureholders;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class NoFloatDataStructBean extends NonAtomDataStruct implements BioBean {
	// A unique identifier for each atom position_
	public List<Integer> get_atom_site_id() {
		return _atom_site_id;
	}
	public void set_atom_site_id(List<Integer> _atom_site_id) {
		this._atom_site_id = _atom_site_id;
	}
	private List<Integer> _atom_site_id =  new ArrayList<Integer>();
	// Atom symbol
	public List<Integer> get_atom_site_Cartn_xInt() {
		return _atom_site_Cartn_xInt;
	}
	public void set_atom_site_Cartn_xInt(List<Integer> _atom_site_Cartn_xInt) {
		this._atom_site_Cartn_xInt = _atom_site_Cartn_xInt;
	}
	public List<Integer> get_atom_site_Cartn_yInt() {
		return _atom_site_Cartn_yInt;
	}
	public void set_atom_site_Cartn_yInt(List<Integer> _atom_site_Cartn_yInt) {
		this._atom_site_Cartn_yInt = _atom_site_Cartn_yInt;
	}
	public List<Integer> get_atom_site_Cartn_zInt() {
		return _atom_site_Cartn_zInt;
	}
	public void set_atom_site_Cartn_zInt(List<Integer> _atom_site_Cartn_zInt) {
		this._atom_site_Cartn_zInt = _atom_site_Cartn_zInt;
	}
	public List<Integer> get_atom_site_B_iso_or_equivInt() {
		return _atom_site_B_iso_or_equivInt;
	}
	public void set_atom_site_B_iso_or_equivInt(List<Integer> _atom_site_B_iso_or_equivInt) {
		this._atom_site_B_iso_or_equivInt = _atom_site_B_iso_or_equivInt;
	}
	public List<Integer> get_atom_site_occupancyInt() {
		return _atom_site_occupancyInt;
	}
	public void set_atom_site_occupancyInt(List<Integer> _atom_site_occupancyInt) {
		this._atom_site_occupancyInt = _atom_site_occupancyInt;
	}
	// All this information is stored as Integer arrays
	private List<Integer> _atom_site_Cartn_xInt = new ArrayList<Integer>();
	private List<Integer> _atom_site_Cartn_yInt  = new ArrayList<Integer>();
	private List<Integer>  _atom_site_Cartn_zInt  = new ArrayList<Integer>();
	// Isotropic atomic displacement parameter
	private List<Integer>  _atom_site_B_iso_or_equivInt  = new ArrayList<Integer>();
	// The fraction of the atom present at this atom position_
	private List<Integer>  _atom_site_occupancyInt  = new ArrayList<Integer>();


}
