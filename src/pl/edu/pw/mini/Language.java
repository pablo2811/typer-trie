package pl.edu.pw.mini;

import java.util.Arrays;

public enum Language {
	english,
	polish,
	german,
	french,
	spanish,
	italian;
	public static String[] names() {
	    return Arrays.toString(Language.values()).replaceAll("^.|.$", "").split(", ");
	}
}
