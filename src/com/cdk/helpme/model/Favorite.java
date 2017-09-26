package com.cdk.helpme.model;

import com.orm.SugarRecord;

public class Favorite extends SugarRecord<Favorite> {
	String type;
	String title;
	String referer;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}