package fr.m2i.models;

public class Task {

	private String _nom;
	private String _description;
	private Integer _id;
	
	
	public String get_nom() {
		return _nom;
	}
	public void set_nom(String _nom) {
		this._nom = _nom;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	@Override
	public String toString() {
		return "Task [_nom=" + _nom + ", _description=" + _description + ", _id=" + _id + "]";
	}
	
	
	
	
	
}
