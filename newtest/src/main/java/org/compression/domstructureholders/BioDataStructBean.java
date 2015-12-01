package org.compression.domstructureholders;

import java.util.ArrayList;
import java.util.List;

public class BioDataStructBean extends NonAtomDataStruct implements BioBean {
	// A unique identifier for each atom position_
	public List<Integer> get_atom_site_id() {
		return _atom_site_id;
	}
	public void set_atom_site_id(List<Integer> _atom_site_id) {
		this._atom_site_id = _atom_site_id;
	}
	private List<Integer> _atom_site_id =  new ArrayList<Integer>();
	// Atom symbol
	public List<Double> get_atom_site_Cartn_x() {
		return _atom_site_Cartn_x;
	}
	public void set_atom_site_Cartn_x(ArrayList<Double> _atom_site_Cartn_x) {
		this._atom_site_Cartn_x = _atom_site_Cartn_x;
	}
	public List<Double> get_atom_site_Cartn_y() {
		return _atom_site_Cartn_y;
	}
	public void set_atom_site_Cartn_y(ArrayList<Double> _atom_site_Cartn_y) {
		this._atom_site_Cartn_y = _atom_site_Cartn_y;
	}
	public List<Double> get_atom_site_Cartn_z() {
		return _atom_site_Cartn_z;
	}
	public void set_atom_site_Cartn_z(ArrayList<Double> _atom_site_Cartn_z) {
		this._atom_site_Cartn_z = _atom_site_Cartn_z;
	}
	public List<Double> get_atom_site_B_iso_or_equiv() {
		return _atom_site_B_iso_or_equiv;
	}
	public void set_atom_site_B_iso_or_equiv(ArrayList<Double> _atom_site_B_iso_or_equiv) {
		this._atom_site_B_iso_or_equiv = _atom_site_B_iso_or_equiv;
	}
	public List<Double> get_atom_site_occupancy() {
		return _atom_site_occupancy;
	}
	public void set_atom_site_occupancy(ArrayList<Double> _atom_site_occupancy) {
		this._atom_site_occupancy = _atom_site_occupancy;
	}
//	public List<Double> get_atom_site_pdbx_formal_charge() {
//		return _atom_site_pdbx_formal_charge;
//	}
//	public void set_atom_site_pdbx_formal_charge(ArrayList<Double> _atom_site_pdbx_formal_charge) {
//		this._atom_site_pdbx_formal_charge = _atom_site_pdbx_formal_charge;
//	}
//	public List<Double> get_atom_site_Cartn_x_esd() {
//		return _atom_site_Cartn_x_esd;
//	}
//	public void set_atom_site_Cartn_x_esd(ArrayList<Double> _atom_site_Cartn_x_esd) {
//		this._atom_site_Cartn_x_esd = _atom_site_Cartn_x_esd;
//	}
//	public List<Double> get_atom_site_Cartn_y_esd() {
//		return _atom_site_Cartn_y_esd;
//	}
//	public void set_atom_site_Cartn_y_esd(ArrayList<Double> _atom_site_Cartn_y_esd) {
//		this._atom_site_Cartn_y_esd = _atom_site_Cartn_y_esd;
//	}
//	public List<Double> get_atom_site_Cartn_z_esd() {
//		return _atom_site_Cartn_z_esd;
//	}
//	public void set_atom_site_Cartn_z_esd(ArrayList<Double> _atom_site_Cartn_z_esd) {
//		this._atom_site_Cartn_z_esd = _atom_site_Cartn_z_esd;
//	}
//	public List<Double> get_atom_site_B_iso_or_equiv_esd() {
//		return _atom_site_B_iso_or_equiv_esd;
//	}
//	public void set_atom_site_B_iso_or_equiv_esd(ArrayList<Double> _atom_site_B_iso_or_equiv_esd) {
//		this._atom_site_B_iso_or_equiv_esd = _atom_site_B_iso_or_equiv_esd;
//	}
//	public List<Double> get_atom_site_occupancy_esd() {
//		return _atom_site_occupancy_esd;
//	}
//	public void set_atom_site_occupancy_esd(ArrayList<Double> _atom_site_occupancy_esd) {
//		this._atom_site_occupancy_esd = _atom_site_occupancy_esd;
//	}

	// Cartesian coordinate components describing the position of this
	// atom site_
	protected List<Double> _atom_site_Cartn_x = new ArrayList<Double>();
	protected List<Double> _atom_site_Cartn_y = new ArrayList<Double>();
	protected List<Double> _atom_site_Cartn_z = new ArrayList<Double>();
	// Isotropic atomic displacement parameter
	protected List<Double> _atom_site_B_iso_or_equiv= new ArrayList<Double>();
	// The fraction of the atom present at this atom position_
	protected List<Double> _atom_site_occupancy= new ArrayList<Double>();
	
//	// GET RID OF THESE
//	// The net integer charge assigned to this atom_
//	protected List<Double> _atom_site_pdbx_formal_charge= new ArrayList<Double>();
//	// Optional uncertainties assoicated with coordinate positions,
//	// occupancies and temperature factors_
//	// Cartesian coordinate components describing the position of this
//	// atom site_
//	protected List<Double> _atom_site_Cartn_x_esd= new ArrayList<Double>();
//	protected List<Double> _atom_site_Cartn_y_esd= new ArrayList<Double>();
//	protected List<Double> _atom_site_Cartn_z_esd= new ArrayList<Double>();
//	// Isotropic atomic displacement parameter
//	protected List<Double> _atom_site_B_iso_or_equiv_esd= new ArrayList<Double>();
//	// The fraction of the atom present at this atom position_
//	protected List<Double> _atom_site_occupancy_esd= new ArrayList<Double>();
}
