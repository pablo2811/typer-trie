package pl.edu.pw.mini;

import java.util.ArrayList;
import java.util.List;

public class Node{
	List<Node> children;
	boolean is_end = false;
	char value;
	
	public Node(char letter){
		this.value = letter;
		this.children = new ArrayList<Node>();
		}
	public void set_end(){
		this.is_end = true;
	}
	public Node is_next(char letter) {
		for(Node x:this.children){
			if(x.value == letter){
				return x;
			}
		}
		return null;
	}
	public void add_child(Node next){
		this.children.add(next);
		return;
	}
	

}
