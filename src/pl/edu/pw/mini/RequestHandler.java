package pl.edu.pw.mini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequestHandler {
	
	HashMap<String,Integer> most_common;
	final HashMap<Integer,ArrayList<String>> len_to_word;
	WordComparator wc;
	public RequestHandler(HashMap<String,Integer> mc,HashMap<Integer,ArrayList<String>> ltw){
		this.most_common = mc;
		this.len_to_word = ltw;
		this.wc = new WordComparator(most_common);
	}
	
	public List<String> completion(int n,Set<String> request){
		Set<String> intersection = new HashSet<String>(request);
		intersection.retainAll(this.most_common.keySet());
		List<String> res = new ArrayList<>(intersection);
		Collections.sort(res,this.wc);
		if(res.size() < n){
			int add = n - res.size();
			for(String s: request) {
				if(!this.most_common.containsKey(s)) {
					res.add(s);
					add--;
				}
				if(add == 0) {
					break;
				}
			}
		}
		return res.subList(0, Math.min(n,res.size()));
	}
	
	static boolean arePermutation(String x, String typo) 
    { 
        int count1[] = new int [512]; 
        Arrays.fill(count1, 0); 
        int count2[] = new int [512]; 
        Arrays.fill(count2, 0); 
        int i; 
        for (i = 0; i < typo.length() ; i++) 
        { 
            count1[x.charAt(i)]++; 
            count2[typo.charAt(i)]++; 
        } 
        for (i = 0; i < 512; i++) 
            if (count1[i] != count2[i]) 
                return false; 
   
        return true; 
    } 
	public int diff_sl(String a,String b) {
		int diff = 0;
		for(int x = 0; x < a.length();x++) {
			if(a.charAt(x) != b.charAt(x)){
				diff++;
			}
		}
		return diff;
	}
	public List<String> permutations(String typo,int n){
		List<String> res = new ArrayList<>();
		int i = 0;
		if(!this.len_to_word.containsKey(typo.length())) {
			return res;
		}
		for(String x: this.len_to_word.get(typo.length())) {
			if(arePermutation(x,typo) && diff_sl(typo,x) <= 3*typo.length()/4){
					res.add(x);
					i++;
			}
			if(i > n){
				return res;
			}
		}
		return res;
	}
	
}
