package me.devjack.chestnetwork.network;

public enum ChestType {
	INPUT("Input"), STORAGE("Storage");
	
	private String name;
	
	ChestType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public static ChestType fromString(String s) {
		for(ChestType type : ChestType.values()) {
			if(type.name.equalsIgnoreCase(s)) return type;
		}
		
		return null;
	}
}
