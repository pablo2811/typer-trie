package pl.edu.pw.mini;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Trie {
	Node root;
	Set<String> request;
	final HashMap<String,Integer> most_common;
	final HashMap<Integer,ArrayList<String>> len_to_word;
	int radius_search = 1;
	int no_sugg = 5;
	RequestHandler rh;
	public Trie(String lang) throws FileNotFoundException{
		this.root = new Node(Character.MIN_VALUE);
		this.request = new HashSet<>();
		this.most_common = new HashMap<>();
		this.len_to_word = new HashMap<>();
		this.bulid_dictionary(lang);
		this.bulid_common(lang);
		rh = new RequestHandler(this.most_common,this.len_to_word);
		}
	
	
	public void bulid_common(String lang) throws FileNotFoundException{
		File f = new File("./resources/common_words/"+lang);
		BufferedReader objReader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
		String strCurrentLine;
		int val = 0;
		try {
			while ((strCurrentLine = objReader.readLine()) != null){
			    this.most_common.put(strCurrentLine, val);
			    val++;
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public void add_word(String word) {
		add_word_util(this.root,word,0);
	}
	
	public void bulid_dictionary(String filename) throws FileNotFoundException{
		File f = new File("./resources/all_words/"+filename);
		BufferedReader objReader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
		String strCurrentLine;
		try {
			while ((strCurrentLine = objReader.readLine()) != null){
				int n = strCurrentLine.length();
				if(this.len_to_word.containsKey(n)){
					ArrayList<String> arr = this.len_to_word.get(n);
					arr.add(strCurrentLine);
					this.len_to_word.put(n, arr);
				}else {
					ArrayList<String> arr = new ArrayList<>();
					arr.add(strCurrentLine);
					this.len_to_word.put(n, arr);
				}
			    this.add_word(strCurrentLine);
			   }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add_word_util(Node current,String word,int index){
		if(index == word.length()){
			current.set_end();
			return;
		}
		Node next = current.is_next(word.charAt(index));
		if(next != null){
			add_word_util(next,word,index+1);
		}else{
			Node n = new Node(word.charAt(index));
			current.add_child(n);
			add_word_util(n,word,index+1);
		}
	}
	
	public List<String> comp(String word,int n){
		this.set_matches(word);
		return this.rh.completion(n, this.request);
	}
	
	public List<String> typo(String word,int n){
		List<String> res1 = this.rh.permutations(word,n);
		int i = 0;
		boolean flag = false;
		String new_word="";
		while(i < word.length()) {
			char current = word.charAt(i);
			new_word += "" + current;
			i++;
			while(i < word.length() && word.charAt(i) == current){
				flag = true;
				i++;
			}
		}
		List<String> res;
		if(flag){
			List<String> res2 = this.rh.permutations(new_word, n-res1.size());
			res = Stream.of(res1, res2)
	                .flatMap(x -> x.stream())
	                .collect(Collectors.toList());
		}else {
			res = res1;
		}
		int radius = 3;
		for(int x = 1; x <= radius && x < word.length(); x++){
			String z = word.substring(x);
			if(in_trie(z) != null) {
				res.add(z);
			}
		}
		for(int x = word.length()-1; x >= word.length()-1-radius && x >=0 ; x--){
			String z = word.substring(0,x);
			if(in_trie(z) != null) {
				res.add(z);
			}
		}
		return res;
	}
	
	public Node in_trie(String word){
		return in_trie_util(this.root,word,0);
		
	}
	public Node in_trie_util(Node current,String word,int index){
		if(index == word.length()){
			if(current.is_end) {
				return current;
			}
			return null;
		}
		Node next = current.is_next(word.charAt(index));
		if(next == null){
			return null;
		}else {
			return in_trie_util(next,word,index+1);
		}
	}
	public void set_matches(String request){
		this.request.clear();
		Node beg = in_trie(request);
		if(beg == null){
			return;
		}else {
			search(beg,request);
		}
	}
	public void search(Node current,String word){
		if(current.is_end) {
			this.request.add(word);
		}
		for(Node x:current.children){
			search(x,word + "" + x.value);
		}
	}
	public void print_matches(String request) {
		set_matches(request);
		for(String x:this.request) {
			System.out.println(x);
		}
	}
	public Set<String> all_matches(String request){
		set_matches(request);
		return(this.request);
	}
	
}
