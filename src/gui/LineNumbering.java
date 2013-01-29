package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
 
 
public class LineNumbering extends JPanel{
	public static JTextArea jta;
	public static JTextArea lines;
	public JScrollPane jsp;
 
	public LineNumbering(){
		//super("Line Numbering Example");
		  //createAndShowGUI();
			//	JPanel frame = new LineNumbering();
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
			 jsp = new JScrollPane();
			jta = new JTextArea();
			lines = new JTextArea("1");
	 
			lines.setBackground(Color.LIGHT_GRAY);
			lines.setEditable(false);
	 
			jta.getDocument().addDocumentListener(new DocumentListener(){
				public String getText(){
					int caretPosition = jta.getDocument().getLength();
					Element root = jta.getDocument().getDefaultRootElement();
					String text = "1" + System.getProperty("line.separator");
					for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
						text += i + System.getProperty("line.separator");
					}
					return text;
				}
		
				public void changedUpdate(DocumentEvent de) {
					lines.setText(getText());
				}
	 
		
				public void insertUpdate(DocumentEvent de) {
					lines.setText(getText());
				}
	 
			
				public void removeUpdate(DocumentEvent de) {
					lines.setText(getText());
				}
	 
			});
			jsp.setColumnHeaderView( new JLabel("Main Display"));
			jsp.getViewport().add(jta);
			jsp.setRowHeaderView(lines);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	 
			this.add(jsp);
		//	frame.pack();
			this.setSize(500,500);
			this.setVisible(true);
	}
 
	public static void Resit(){

	}
 
	public static void main(String[] args){

	}
}