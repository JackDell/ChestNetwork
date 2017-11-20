package me.devjack.chestnetwork.network;

public enum NetworkColor {

	WHITE("White"), ORANGE("Orange"), MAGENTA("Magenta"), BLUE("Blue"), LIGHT_BLUE("Light_Blue"), CYAN("Cyan"),
	YELLOW("Yellow"), GREEN("Green"), LIGHT_GREEN("Light_Green"), GRAY("Gray"), LIGHT_GRAY("Light_Gray"),
	PURPLE("Purple"), PINK("Pink"), RED("Red"), BROWN("Brown"), BLACK("Black");
	
	private String name;
	
	NetworkColor(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public static NetworkColor fromString(String s) {
		for(NetworkColor nc : NetworkColor.values()) {
			if(nc.name.equalsIgnoreCase(s)) return nc;
		}
		
		return null;
	}
}
