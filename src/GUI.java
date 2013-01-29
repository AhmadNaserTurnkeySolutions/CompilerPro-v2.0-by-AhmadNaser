
import gui.ImagePanel;
import gui.JScrollPane2;
import gui.JTable2;
import gui.LineNumbering;
import gui.TransparentPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class GUI extends JFrame implements ActionListener {
	JButton ldBtn;
	JButton extBtn;
	JTabbedPane tabbedPane;
	JPanel bottomPanel;
//	JTextArea textArea ;
	Parser p;
	ImagePanel panel1;
	ImagePanel panel2;
	DefaultTableModel tb;
	JButton btnMin;
	LineNumbering JTextArea_Line;
	 private javax.swing.JButton jButton1;
	    private JScrollPane2 jScrollPane1;
	    private JTable2 jTable1;
public GUI ()
{

this.setTitle("Compiler Project");	
 tabbedPane = new JTabbedPane();


ldBtn=new JButton("Read File");
extBtn=new JButton("Exit");
 bottomPanel=new JPanel(new FlowLayout());
bottomPanel.setBackground(Color.white);
bottomPanel.add(ldBtn);
bottomPanel.add(extBtn);
panel1 = new ImagePanel();


panel1.setLayout(new GridLayout(1,3));
this.panel1.setImage(Toolkit.getDefaultToolkit().getImage("img//b1.jpg"));

//textArea = new JTextArea(10, 30);

//pp2.setSize(400,400);
//JScrollPane scrollPane = new JScrollPane(textArea); 
/*JScrollPane scrollPane = new JScrollPane(pp2); 
textArea.setEditable(true);
scrollPane.setBorder(new LineBorder(Color.black));

JViewport viewport = new JViewport();
viewport.setView(null);
scrollPane.setColumnHeaderView( new JLabel("Main Display"));
scrollPane.setRowHeaderView(viewport);*/
//panel1.setPreferredSize(new Dimension(900, 800));
panel1.add(new TransparentPanel());
JTextArea_Line=new LineNumbering();
JTextArea_Line.setLayout(new GridLayout(1,1));
JTextArea_Line.setBorder(new LineBorder(Color.black));
panel1.add(JTextArea_Line);

panel1.add(new TransparentPanel());

//pp.set

tabbedPane.addTab("Tab 1", null, panel1,
"Does nothing");




panel2 = new ImagePanel();
panel2.setLayout(new GridLayout(1,1));
this.panel2.setImage(Toolkit.getDefaultToolkit().getImage("img//b2.jpg"));
panel2.setPreferredSize(new Dimension(900, 800));
panel2.setSize(900, 800);
jScrollPane1 = new JScrollPane2();
jTable1 = new JTable2();
jButton1 = new javax.swing.JButton();

this.tb=new DefaultTableModel();
tb.setDataVector(new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
    }, new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
    });
this.jTable1.setModel(tb);
jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    /*
jTable1.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
    },
    new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
    }
));*/
jScrollPane1.setViewportView(jTable1);

jButton1.setText("load");
jButton1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
    	/*tb.setDataVector( p.parser.parsingTable
    	    , p.parser.terminals);
    	    */
    	Object o[][]=	new Object [p.parser.nonTerminals.length][1000];
    	for(int i=0;i<p.parser.nonTerminals.length;i++)
    	{
    		o[i][0]=new String(p.parser.nonTerminals[i].toString());
    		for(int j=1;j<=p.parser.parsingTable[i].length;j++)
    		{
    			if(p.parser.parsingTable[i][j-1].rightSide=="Error")
    			{
    				o[i][j]="Error";
    			}
    			else
    			{
    				String x1=p.parser.parsingTable[i][j-1].rightSide;
    				x1=x1.replaceAll("@", " ");
    			o[i][j]=new String(p.parser.parsingTable[i][j-1].leftSide+"->"+x1);
    		}
    		}
    		
    	
    	}
    	String []t=new String[p.parser.terminals.length+1];
    	t[0]="Vn/Vt";
    	for(int i=1;i<t.length;i++)
    	{
    		t[i]=p.parser.terminals[i-1];
    	}
    	tb.setDataVector( o
        	    , t);
    	tabbedPane.setSelectedIndex(1);
  //  p.parser.terminals
    }
});

btnMin=new JButton("Min");


btnMin.addActionListener(this);


this.panel2.add(this.jScrollPane1);
this.bottomPanel.add(jButton1);
this.bottomPanel.add(btnMin);
tabbedPane.addTab("Tab 2", null, panel2,
                  "Does nothing");




this.add(tabbedPane,BorderLayout.CENTER);
this.add(bottomPanel,BorderLayout.SOUTH);








ldBtn.addActionListener(this);
extBtn.addActionListener(this);
this.setVisible(true);
this.setLocationRelativeTo(null);
this.pack();

this.validate();
this.repaint();
this.fullScreen();
this.repaint();
this.panel1.repaint();
this.panel2.repaint();
this.validate();
this.panel1.validate();
this.panel2.validate();
//this.repaint();



Timer timer = new Timer();
MyTimerTask task=new MyTimerTask(this,this.panel1,this.panel2);

    timer.schedule(task, 0, 100);
  //  JOptionPane.showMessageDialog(null,"Welcome");

}
	
	
public static void main (String args[])
{

		   GUI g=new GUI();
	
}

public void actionPerformed(ActionEvent e) {
	
	if(e.getSource()==this.btnMin){
	setState(1);

		//addPanel1();
		
	}
		if(e.getSource()==this.extBtn)
	{
		System.exit(0);
	}
	if(e.getSource()==this.ldBtn)
	{
		//reset
		addPanel1();
		//JTextArea_Line.lines=new JTextArea();
		//JTextArea_Line.jsp=	new JScrollPane();
	
		String sourceCode = "";
	
		 try {
			 JFileChooser jf=new JFileChooser(".");
	         jf.showOpenDialog(null);
	      

		// get the name of the input file	

			   File file = jf.getSelectedFile();
			
			   Scanner scanner = new Scanner(file);
			   while (scanner.hasNextLine()) {
				   String tmp=scanner.nextLine();
				   if(tmp.equals(""))
				   {
					   tmp=" ";
				   }
			    sourceCode += tmp+"\n";
			   }
			   
			   scanner.close();
			  } catch (FileNotFoundException ex) {
			   ex.printStackTrace();
			  }
			  this.JTextArea_Line.jta.setText(sourceCode);
			  
	  Scanneree s = new Scanneree();
	  s.setSource(sourceCode);
	   p = new Parser(s);	
	   //JOptionPane.showMessageDialog(null, p.errorMessage);
	   String ss=this.JTextArea_Line.jta.getText();
	   String str=ss+"\n*****\n\n"+p.errorMessage;
	   this.JTextArea_Line.jta.setText(str);
	   int end=str.length();
	   int start=0;
	   int counter=0;
	   for(int i=0;i<end;i++)
	   {
		   if((str.charAt(i)+"").equals("*"))
		   {
			   counter++;
			   if(counter==5)
			   {
				   start=i;
				   break;
			   }

		   }
		   else
		   {
counter=0;
		   }
	   }
	   System.out.print(str.length());
	 
	 
	   try {
		this.JTextArea_Line.jta.getHighlighter().addHighlight(start+1,
		          end + "hi".length(),
		           new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
	} catch (BadLocationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   
	   this.JTextArea_Line.jta.scrollRectToVisible(new Rectangle(0, 0, 	   this.JTextArea_Line.jsp.getViewport().getWidth(), 	   this.JTextArea_Line.jsp.getViewport().getHeight()));
	   this.JTextArea_Line.jta.setCaretPosition(10);
	
	
	   
	
	}
	
}


public void fullScreen(){
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gs = ge.getDefaultScreenDevice();
    Window win = new Window(this);//add jframe to window component
win.setBackground(Color.black);
//  this.imagePanel1.setBackground(Color.black);
 this.setBackground(Color.black);
  win.add(this.tabbedPane,BorderLayout.CENTER);
  win.add(this.bottomPanel,BorderLayout.SOUTH);
 
  win.repaint();


 //    win.add(this.imagePanel1,BorderLayout.CENTER);
    //this.imagePanel1.setImage(new ImageIcon(this.getClass().getResource("BACKF.jpg")).getImage());
  //        this.imagePanel2.setImage(new ImageIcon(this.getClass().getResource("BACKt.jpg")).getImage());
    //        this.HashPanel.setImage(new ImageIcon(this.getClass().getResource("BACKF.jpg")).getImage());
    win.validate();
    
    gs.setFullScreenWindow(win);
  
         
    
}
public void addPanel1()
{
	this.panel1.removeAll();
	this.panel1.repaint();
		//this.remove(this.panel1);
		this.repaint();
	panel1 = new ImagePanel();


	panel1.setLayout(new GridLayout(1,3));
	this.panel1.setImage(Toolkit.getDefaultToolkit().getImage("img//b1.jpg"));

	//textArea = new JTextArea(10, 30);

	//pp2.setSize(400,400);
	//JScrollPane scrollPane = new JScrollPane(textArea); 
	/*JScrollPane scrollPane = new JScrollPane(pp2); 
	textArea.setEditable(true);
	scrollPane.setBorder(new LineBorder(Color.black));

	JViewport viewport = new JViewport();
	viewport.setView(null);
	scrollPane.setColumnHeaderView( new JLabel("Main Display"));
	scrollPane.setRowHeaderView(viewport);*/
	//panel1.setPreferredSize(new Dimension(900, 800));
	panel1.add(new TransparentPanel());
	JTextArea_Line=new LineNumbering();
	JTextArea_Line.setLayout(new GridLayout(1,1));
	JTextArea_Line.setBorder(new LineBorder(Color.black));
	panel1.add(JTextArea_Line);

	panel1.add(new TransparentPanel());


	//pp.set
	tabbedPane.remove(0);
	//tabbedPane.add
	//tabbedPane.addTab("Tab 1", null, panel1,"Does nothing");
	

	tabbedPane.add(panel1,0);
	tabbedPane.setTitleAt(0, "Tab 1");
	tabbedPane.setSelectedIndex(0);
	
	tabbedPane.repaint();
	panel1.repaint();
	this.repaint();
	}
}
