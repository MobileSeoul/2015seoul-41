package com.cdk.helpme.model;

public class Hospital {
	String id;
	String name;
	double lat;
	double lon;
	String tel;
	int availble;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getAvailble() {
		return availble;
	}

	public void setAvailble(int availble) {
		this.availble = availble;
	}

}
