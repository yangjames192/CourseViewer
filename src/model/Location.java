package model;

public class Location {
	private String building;
	private String room;
	
	public Location(String bu, String ro) {
		this.building = bu;
		this.room = ro;
	}
	
	public String getBuilding() {
		return building;
	}
	
	public String getRoom() {
		return room;
	}
}
