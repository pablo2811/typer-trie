package pl.edu.pw.mini;

import java.util.Comparator;
import java.util.HashMap;

public class WordComparator implements Comparator<String>{
	
	HashMap<String,Integer> comp;
	public WordComparator(HashMap<String,Integer> map) {
		this.comp = map;
	}
	@Override
	public int compare(String arg0, String arg1) {
		return this.comp.get(arg0).compareTo(this.comp.get(arg1));
	}

}
