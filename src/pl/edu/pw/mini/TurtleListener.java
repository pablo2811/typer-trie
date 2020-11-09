package pl.edu.pw.mini;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
public class TurtleListener implements DocumentListener {

	JTextArea t;
	Dictionary dict;
	JList jsp;
	public TurtleListener(JTextArea t) throws FileNotFoundException {
		this.t = t;
		dict = new Dictionary();
		jsp = new JList();
	}
	@Override
	public void changedUpdate(DocumentEvent arg0){
		jsp.setVisible(false);
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
//		jsp.setVisible(false);
		try {
			int i = arg0.getOffset();
			String curr = t.getText(i, 1);
			String s1 = "";
			String s2 = "";
			int j = i-1;
			while(j >= 1 && t.getText().charAt(j) != ' ') {
				s1 += "" + t.getText().charAt(j);
				j--;
			}
			StringBuilder sb = new StringBuilder(s1);
			s1 = sb.reverse().toString();
			int z = i+1;
			while(t.getText().length() > z && t.getText().charAt(z) != ' ') {
				s2 += "" + t.getText().charAt(z);
				z++;
			}
			if(curr.equals(" ")){
				// words s1,s2 are "finished" we need to check againts typos and notify()
				if(!s1.equals("")){
//					this.spell_check.add(new Pair(s1,j+1));
					List<String> prop = dict.typoCompletion(s1);
		            JFrame frame = (JFrame) SwingUtilities.getRoot(component);

				}
				if(!s2.equals("")){
					List<String> prop = dict.typoCompletion(s2);
					add_buttons(prop,i+1);
//					this.spell_check.add(new Pair(s2,i+1));
				}
				
			}else{
				// word s1+s2 is "in progress" and we can suggest.
//				this.suggestion.add(new Pair(s1+s2,j+1));
				List<String> sug = dict.completion(s1+s2);
				add_buttons(sug,j+1);
			}
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // where the change has been made
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		jsp.setVisible(false);
		
	}
	public void add_buttons(List<String> exchangers,int position) {
		jsp.removeAll();
		t.
		for(String s:exchangers) {
			JButton jb = new JButton(s);
			jsp.add(jb);
		}
		jsp.setLayout(new BoxLayout(jsp, BoxLayout.Y_AXIS));
//		jsp.setSize(w,h);
		jsp.setVisible(true);
//		jsp.setBounds(100, 100, h/10, w/10);
		t.getParent().add(jsp);
		
	}
	
	
	

}
