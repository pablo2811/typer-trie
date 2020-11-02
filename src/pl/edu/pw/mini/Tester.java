package pl.edu.pw.mini;

import java.io.FileNotFoundException;
import java.util.List;

public class Tester {
	public static void main(String[] args) throws FileNotFoundException{
		Trie t = new Trie("english");
		List<String> x = t.n_best_match("comp",20);
		System.out.println(x);
		
	}
}
