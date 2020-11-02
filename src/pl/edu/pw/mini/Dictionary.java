package pl.edu.pw.mini;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Dictionary {
	HashMap<Language,Trie> map;
	public Dictionary() throws FileNotFoundException{
		this.map = new HashMap<>();
		this.bulid_dicts();
		
	}
	public void bulid_dicts() throws FileNotFoundException{
		for(Language lang: Language.values()){
			Trie t = new Trie(lang.toString());
			map.put(lang, t);
		}
	}
}
