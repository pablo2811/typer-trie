package pl.edu.pw.mini;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
public class Tester {
	Dictionary d;
	Tester() throws FileNotFoundException{
		d = new Dictionary();
		d.change_lang("polish");
	}
	public static void main(String[] args) throws FileNotFoundException{
		Tester t = new Tester();
//		t.test_completion();
		t.test_typo();
	}
	public void test_completion() {
		String str;
		Scanner sc = new Scanner(System.in);
		while(!((str=sc.next()).equals("exit"))) {
			List<String> x = d.completion(str);
			System.out.println(x);
		}
	}
	public void test_typo() {
		String str;
		Scanner sc = new Scanner(System.in);
		while(!((str=sc.next()).equals("exit"))) {
			List<String> x = d.typoCompletion(str);
			System.out.println(x);
		}
	}
}
