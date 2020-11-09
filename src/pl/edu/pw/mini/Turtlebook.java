package pl.edu.pw.mini;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import net.miginfocom.swing.MigLayout;

public class Turtlebook extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	static int WIDTH = 1000;
	static int HEIGTH = 1000;
	static String name = "Turtlebook";
	static String[] file = {"New file","Open file","Save file","Quit"};
	static String[] edit = {"Copy","Paste","Cut"};
	static String[] settings = {"Language","Theme","Font"};
	JTextArea text;
	JMenuBar jmb;
	Color font_color;
	Color bg_color;
	JPopupMenu words;
	Dictionary dict;
	public Turtlebook() throws FileNotFoundException{
		super(name);
		dict = new Dictionary();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH,HEIGTH);
		setResizable(true);
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		text = new JTextArea();
//		font_color = Color.white;
//		bg_color = Color.DARK_GRAY;
		text.setForeground(font_color);
		text.setBackground(bg_color);
		jmb = new JMenuBar();
		jmb.add(simple_create_menu(file,"File"));
		jmb.add(simple_create_menu(edit,"Edit"));
		jmb.add(simple_create_menu(settings,"View"));
		words = new JPopupMenu();
		text.getDocument().addDocumentListener(new DocumentListener(){
			
			int workaround = 0;
			JPanel btnPanel = new JPanel();
			@Override
			public void changedUpdate(DocumentEvent arg0) {}
			
			@Override
			public void insertUpdate(DocumentEvent arg0){
				String t = text.getText();
				if(t.length() == 0) {
					return;
				}
				int i = arg0.getOffset();
				char c = t.charAt(i + workaround);
				String s = get_word(t, i-1);
				StringBuilder sb = new StringBuilder(s);
				s = sb.reverse().toString();
				List<String> prop = null;
				if(c == ' '){
					prop = dict.typoCompletion(s);
				}else{
					if(workaround == 0) {
						s += "" + c;
					}
					System.out.println(s);
					prop = dict.completion(s);
					if(prop == null) {
						prop = dict.typoCompletion(s);
					}
				}
				if(prop != null) {
					System.out.println(prop);
					show_words(prop);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent arg0){
				System.out.println(text.getText().length());
				workaround = -1;
				insertUpdate(arg0);
				workaround = 0;
			}
			private void show_words(List<String>prop){
				btnPanel.removeAll();
				int w = Turtlebook.this.getWidth();
				int h = Turtlebook.this.getHeight();
				int i = 1;
				int char_width = w/100;
				int fixed_height = h/10;
				int curr_width = 5;
				for(String s:prop){
					int wrd = (int) char_width*s.length();
					JButton jb = new JButton(s);
				//	jb.setBounds(curr_width,h - fixed_height,wrd,fixed_height);
					jb.setBounds(50,500,wrd,fixed_height);
					btnPanel.add(jb);
					i++;
					curr_width += wrd;
					
				}
				
				Turtlebook.this.add(btnPanel);
			}
			public String get_word(String s,int j) {
				String res = "";
				while(j >= 0 && (Character.isWhitespace(s.charAt(j)) == false)){
					res += "" + s.charAt(j);
					j--;
				}
				return res;
			}
		});
		this.add(text);
		this.setJMenuBar(jmb);
		setResizable(true);
		setVisible(true);
	}
	public JMenu simple_create_menu(String[] options,String name){
		JMenu jm = new JMenu(name);
		for(String op:options) {
			JMenuItem n = new JMenuItem(op);
			jm.add(n);
		}
		return jm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
