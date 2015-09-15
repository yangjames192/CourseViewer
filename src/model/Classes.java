package model;

import java.util.List;

public class Classes {
	private Dates dates;   //schedule date
	private Location location;  //schedule location
	private List<String> instructors;
	
	//constructor
	public Classes(Dates d, Location l, List<String> ins) {
		this.dates = d;
		this.location = l;
		this.instructors = ins;
	}
	
	public Dates getDates() {
		return dates;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public List<String> getInstructors() {
		return instructors;
	}
	
}
