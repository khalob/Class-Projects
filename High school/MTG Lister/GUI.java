import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
 
public class GUI {
	//Data data = new Data();
    JFrame frameToPrint;
    JTextArea inputArea;
    static int halfWidth = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2;
    static int halfHeight  = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2;
    static Data data = new Data();
    static String file;
    public static void main(String[] args) {
		menu();
	}
    
    public GUI(JFrame f) {
        frameToPrint = f;
    }
    
    public static void menu(){
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        final JFrame f = new JFrame("");
        f.setPreferredSize(new Dimension(400,200));
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        f.setLocation(halfWidth-200,halfHeight-100);
        String username = data.readUserName();
        
        //Welcoming GUI
        JLabel welcome = new JLabel("Welcome "+ username + "! What would you like to change?\n",SwingConstants.CENTER); 
        f.getContentPane().add(welcome, BorderLayout.NORTH);
        
        JPanel subPanel = new JPanel();
        GridLayout lay1 = new GridLayout(2,2);
        subPanel.setLayout(lay1);
        
    	JButton your = new JButton("Your deck list");
    	JButton borrowed = new JButton("Borrowed deck list");
    	JButton lent = new JButton("Lent deck list");
    	JButton changeUsername = new JButton("Change Username");
    	
    	JButton instructions = new JButton("Instructions");
        
    	your.addActionListener(new ActionListener(){
   		   public void actionPerformed(ActionEvent e) {
   			   if(e.getActionCommand() == "Your deck list"){
   				   f.dispose();
   				   file = e.getActionCommand().replace(" ","") + ".txt";
   				   addRemove(file);
   			   }
  		   }
     	});
     	borrowed.addActionListener(new ActionListener(){
   		   public void actionPerformed(ActionEvent e) {
   			   if(e.getActionCommand() == "Borrowed deck list"){
   				   f.dispose();
   				   file = e.getActionCommand().replace(" ","") + ".txt";
   				   addRemove(file);
   			   }
  		   }
     	});
     	lent.addActionListener(new ActionListener(){
   		   public void actionPerformed(ActionEvent e) {
   			   if(e.getActionCommand() == "Lent deck list"){
   				   f.dispose();
   				   file = e.getActionCommand().replace(" ","") + ".txt";
   				   addRemove(file);
   			   }
  		   }
     	}); 
        changeUsername.addActionListener(new ActionListener(){
    		   public void actionPerformed(ActionEvent e) {
    			   if(e.getActionCommand() == "Change Username"){
    				   f.dispose();
    				   changeUsername();
    			   }
   		   }
      	});
        
    	instructions.addActionListener(new ActionListener(){
    		   public void actionPerformed(ActionEvent e) {
    			   if(e.getActionCommand() == "Instructions"){
    				   f.dispose();
    				   instructions();
    			   }
   		   }
      	});
        
        subPanel.add(your);
        subPanel.add(borrowed);
        subPanel.add(lent);
        subPanel.add(changeUsername);
        
        f.getContentPane().add(instructions, BorderLayout.SOUTH);
        f.add(subPanel);    
        f.pack();
        f.setVisible(true);
    }
 
    
    public static void addRemove(String fileName) {
		String type = fileName.replace("List.txt", "");
		Scanner inputStream = null;
		//read
		try{
			inputStream = new Scanner(new File(fileName)); //try to open the file
		}
		catch(Exception e){
			System.out.println("There is not a list for "+type+" cards.");
			try {
				System.out.println("Making a new list.");
				PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName,true));
				outputStream.close();
				inputStream = new Scanner(new File(fileName));
			} catch (Exception e1) {
				System.out.println("CANNOT MAKE FILE!");
			}
			
		}
		
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        final JFrame f = new JFrame("");
        //f.setPreferredSize(new Dimension(400,85));
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JTextArea text = new JTextArea(15, 20);
        if(inputStream.hasNextLine()==true){
        	while(inputStream.hasNextLine()){
        		text.append(inputStream.nextLine() + "\n");
        	}
        }
        text.setBackground(Color.LIGHT_GRAY);
        JScrollPane pane = new JScrollPane(text);
        pane.setPreferredSize(new Dimension(250,200));
        f.add("Center", pane);
        text.setEditable(false);
        f.setPreferredSize(new Dimension(400,200));
        f.setLocation(halfWidth-200,halfHeight-100);
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        file = fileName;
    	JLabel title = new JLabel("Would you like to add or remove?",SwingConstants.CENTER); 
    	JButton add = new JButton("Add");
    	JButton remove = new JButton("Remove");
    	JButton menu = new JButton("Main menu");
    	f.add("North",title);
    	f.add("West",add);
    	f.add("East",remove);
    	f.add("South",menu);
    	add.addActionListener(new ActionListener(){
    		   public void actionPerformed(ActionEvent e) {
    			   if(e.getActionCommand() == "Add"){
    				    f.dispose();
    					adding(file);
    				}
    		   }
    	});		   
    	remove.addActionListener(new ActionListener(){
 		   public void actionPerformed(ActionEvent e) {
			   if(e.getActionCommand() == "Remove"){
				    f.dispose();
					removing(file);
				}
		   }
    	});
    	menu.addActionListener(new ActionListener(){
  		   public void actionPerformed(ActionEvent e) {
 			   if(e.getActionCommand() == "Main menu"){
 				    f.dispose();
 					menu();
 				}
 		   }
    	});
        f.pack();
        f.setVisible(true);
        inputStream.close();
    }
    
    
    public static void adding(String fileName) {
    	UIManager.put("OptionPane.okButtonText", "Add to list");
    	UIManager.put("OptionPane.cancelButtonText", "Cancel");
    	String input ="";
    	while(input.equals("")){
    		input = JOptionPane.showInputDialog("What would you like to add to the list? (Include the amount)");
    		if(input != null){
    			input = input.toLowerCase();
    		}else{
    			addRemove(fileName);
    			return;
    		}
    	}
    	Data.listWrite(input, fileName);
    	addRemove(fileName);
    }
    
    
    public static void removing(String fileName) {
    	//printList(fileName);
    	UIManager.put("OptionPane.okButtonText", "Remove from list");
    	UIManager.put("OptionPane.cancelButtonText", "Cancel");
    	String input ="";
    	while(input.equals("")){
    		input = JOptionPane.showInputDialog("What would you like to remove from the list? (Include the amount)");
    		if(input != null){
    			input = input.toLowerCase();
    		}else{
    			addRemove(fileName);
    			return;
    		}
    	}
    	if(!input.matches(".*\\d.*")){
			input = "1x " + input; 
		}
    	Data.listRemove(input, fileName, 0);
    	addRemove(fileName);
    }
    
    public static void changeUsername(){
    	UIManager.put("OptionPane.okButtonText", "Set name");
    	UIManager.put("OptionPane.cancelButtonText", "Close");
    	String input ="";
    	input = JOptionPane.showInputDialog("What would you like your username to be?");
    	if(input != null && input.matches(".*\\d.*") || input != null && input.trim().length() > 0){
    		data.writeUsername(input);
    	}
    	menu();
    }
	
    public static void instructions(){
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        final JFrame f = new JFrame("");
        //f.setPreferredSize(new Dimension(400,85));
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JTextArea text = new JTextArea(15, 20);

        
        
        
        
        
        
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        StyledDocument doc = (StyledDocument) textPane.getDocument();

        Style style = doc.addStyle("StyleName", null);
        Style Orignalstyle = doc.addStyle("StyleName", null);
        StyleConstants.setBold(style, true);
        
        
        try {
			
        	doc.insertString(doc.getLength(), "To add or remove cards you simply click the [add] or [remove] button under the appropriate list type. To add multiple cards follow this format:", Orignalstyle);
        	doc.insertString(doc.getLength(), " [number of cards]x [name of card]", style);
        	doc.insertString(doc.getLength(), ". For example, if I want to add 4 snapcasters to my deck, I can type the follow in the input box: ", Orignalstyle);
        	doc.insertString(doc.getLength(), "\n                                                   4x snapcaster", style);
        	doc.insertString(doc.getLength(), "\n                   [number of cards]x [name of card]", style);
        	doc.insertString(doc.getLength(), "\nThe program includes automatic capitalization and will also auto add or remove items if you type at least half of the word.", Orignalstyle);
        } catch (BadLocationException e1) {
			System.out.println("error");
		}

        
        
        textPane.setBackground(Color.LIGHT_GRAY);
        
        
        
        
        
        
        JScrollPane pane3 = new JScrollPane(text);
        JTextPane pane = new JTextPane();
        pane.setPreferredSize(new Dimension(250,200));

        text.setEditable(false);
        text.append("To add cards you simply click the *add* button under the appropriate list type. To add multiple cards follow this format: [number of cards]x [name of card]. \nFor example, if I wanted to add 4 snapcasters to my deck, I would type the follow in the input box: 4x snapcaster\n\n The program includes automatic capitalization and will also auto add or remove items if you type at least half of the word.");
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        f.setPreferredSize(new Dimension(400,200));
        f.setLocation(halfWidth-200,halfHeight-100);
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        
        JButton menu = new JButton("Main menu");
        
        
    	menu.addActionListener(new ActionListener(){
   		   public void actionPerformed(ActionEvent e) {
  			   if(e.getActionCommand() == "Main menu"){
  				    f.dispose();
  					menu();
  				}
  		   }
     	});
        f.add("Center", textPane);
        f.add("South",menu);
        f.pack();
        f.setVisible(true);
    }
}
