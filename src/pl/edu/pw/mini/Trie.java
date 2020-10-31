package pl.edu.pw.mini;

public class Trie {
	Node root;
	public Trie(){
		this.root = new Node(Character.MIN_VALUE);
	}
	
	public void add_word(String word) {
		add_word_util(this.root,word,0);
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
	public Node in_trie_util(Node current,String word,int index) {
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
	

}
