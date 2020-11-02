package pl.edu.pw.mini;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trie {
	Node root;
	Set<String> request;
	final HashMap<String,Integer> most_common;
	public Trie(String lang) throws FileNotFoundException{
		this.root = new Node(Character.MIN_VALUE);
		this.request = new HashSet<>();
		this.most_common = new HashMap<>();
		this.bulid_dictionary(lang);
		this.bulid_common(lang);
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
			while ((strCurrentLine = objReader.readLine()) != null) {
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
	public Node in_trie(String word){
		return in_trie_util(this.root,word,0);
		
	}
	public Node in_trie_util(Node current,String word,int index){
		if(index == word.length()){
			return current;
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
	
	
	public List<String> n_best_match(String request,int n){
		set_matches(request);
		Set<String> intersection = new HashSet<String>(this.request);
		intersection.retainAll(this.most_common.keySet());
		List<String> res = new ArrayList<>(intersection);
		Collections.sort(res,new Comparator<String>(){
			@Override
			public int compare(String arg0, String arg1) {
				return most_common.get(arg0).compareTo(most_common.get(arg1));
			}
			
		});
		if(res.size() < n){
			int add = n - res.size();
			for(String s: this.request) {
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

}
