package org.btpos.dj2addons.util;

public interface TextFormat {
	String BLACK = encode("0");
	String DARK_BLUE = encode("1");
	String DARK_GREEN = encode("2");
	String DARK_AQUA = encode("3");
	String DARK_RED = encode("4");
	String DARK_PURPLE = encode("5");
	String GOLD = encode("6");
	String GRAY = encode("7");
	String DARK_GRAY = encode("8");
	String BLUE = encode("9");
	String GREEN = encode("a");
	String AQUA = encode("b");
	String RED = encode("c");
	String LIGHT_PURPLE = encode("d");
	String YELLOW = encode("e"); // #FFFF55
	String WHITE = encode("f");
	String OBFUSCATED = encode("k");
	String BOLD = encode("l");
	String STRIKE = encode("m");
	String UNDERLINE = encode("n");
	String ITALIC = encode("o");
	String RESET = encode("r");
	
	static String encode(String s) {
		return "§" + s;
	}
}
