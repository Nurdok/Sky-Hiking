package com.rachum.amir.skyhiking;

public enum Card {
	RED("red"), GREEN("green"), YELLOW("yellow"), PURPLE("purple"), NONE(""), WILD("wild");
	
	private String name;
	
	private Card(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
