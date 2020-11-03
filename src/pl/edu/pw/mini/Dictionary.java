package pl.edu.pw.mini;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Dictionary {
	HashMap<Language,Trie> map;
	Language current;
	int N;
	public Dictionary() throws FileNotFoundException{
		this.map = new HashMap<>();
		this.current = Language.english;
		this.bulid_dict(this.current);
		this.N = 5;
		
	}
	public void bulid_dict(Language lang) throws FileNotFoundException{
		Trie t = new Trie(lang.toString());
		map.put(lang, t);
	}
	public void change_lang(String l) throws FileNotFoundException{
		String[] all = Language.names();
		if(Arrays.asList(all).contains(l)){
			this.current = Language.valueOf(l);
			if(!map.keySet().contains(this.current)){
				bulid_dict(this.current);
			}
			
		}
	}
	public boolean is_in_dictionary(String req){
		return (this.map.get(this.current).in_trie(req.toLowerCase()) != null) ? true: false;
	}
	
	public void to_Upper_list(List<String> list) {
		for(int x = 0; x < list.size();x++) {
			String w = list.get(x);
			w = w.substring(0, 1).toUpperCase() + w.substring(1);
			list.set(x, w);
		}
	}
	
	public List<String> completion(String req){
		List <String> res = this.map.get(this.current).comp(req.toLowerCase(), this.N);
		if(Character.isUpperCase(req.charAt(0))){
			to_Upper_list(res);
		}
		return res;
	}
	public List<String> typoCompletion(String req){
		List<String> res =  this.map.get(this.current).typo(req.toLowerCase(), this.N);
		if(Character.isUpperCase(req.charAt(0))){
			to_Upper_list(res);
		}
		return res;
	}
	
	
}
