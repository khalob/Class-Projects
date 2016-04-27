import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

//  Khalob Cognata 4/6/2016
//  Finds the area of a rectangle given the top left and bottom right points

public class HW0405 extends JFrame{
    static int halfWidth = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2;
    static int halfHeight  = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2;
	static int frameWidth = 500;
	static int frameHeight = 150;
    String calculate (String inString) {
		Double radius = Double.valueOf(inString);		
		Double areaOfCircle = radius * radius * Math.PI;
		return areaOfCircle.toString();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Find the area of a rectangle");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameWidth, frameHeight);
    	frame.setLayout(new BorderLayout());
    	frame.setLocation(halfWidth-(frameWidth/2),halfHeight-(frameHeight/2)); //centered on screen
    	
    	JPanel buttonPanel = new JPanel();
    	frame.add(buttonPanel, BorderLayout.SOUTH);
    	
    	JPanel northPanel = new JPanel();
    	northPanel.setLayout(new GridLayout(3, 0));
    	frame.add(northPanel, BorderLayout.CENTER);
    	
    	
    	JPanel inPanel = new JPanel();
    	inPanel.setLayout(new GridLayout(1, 3));
    	northPanel.add(inPanel);
    	
    	JPanel outPanel = new JPanel(new GridLayout(1, 3));
    	northPanel.add(outPanel);
    	
    	JLabel ans = new JLabel("Answer: ");
    	northPanel.add(ans);

    	JLabel label = new JLabel("Top Left Corner  ");
    	inPanel.add(label);
    	
    	JTextField inField = new JTextField("X");
		inField.setEditable(true);
		inPanel.add(inField);
		
    	JTextField outField = new JTextField("Y");
    	outField.setEditable(true);
    	inPanel.add(outField);
		
    	JLabel label2 = new JLabel("Lower Right Corner ");
    	outPanel.add(label2);
    	
    	JTextField inField2 = new JTextField("X");
		inField2.setEditable(true);
		outPanel.add(inField2);
    	
    	JTextField outField2 = new JTextField("Y");
    	outField2.setEditable(true);
    	outPanel.add(outField2);
    	
    	JButton randButton = new JButton("Generate random");
    	
    	//Action listener for random button
    	randButton.addActionListener(new ActionListener(){
 		   public void actionPerformed(ActionEvent e) {
 			   if(e.getActionCommand() == "Generate random"){
 				  Random rand = new Random();

 				int n1 = rand.nextInt(100) + 1;
 				int n2 = rand.nextInt(100) + 1;
 				int n3 = rand.nextInt(100) + 1;
 				int n4 = rand.nextInt(100) + 1;
 				
 				inField.setText(""+n1);  
 				outField.setText(""+n2);  
 				inField2.setText(""+n3);  
 				outField2.setText(""+n4);  
 				}
 		   }
    	});
    	
    	buttonPanel.add(randButton);
    	
    	JButton calcButton = new JButton("Calculate area");
    	
    	calcButton.addActionListener(new ActionListener(){
  		   public void actionPerformed(ActionEvent e) {
  			   if(e.getActionCommand() == "Calculate area"){
  				   if(inField.getText().matches("^-?\\d+$") && inField2.getText().matches("^-?\\d+$") && outField.getText().matches("^-?\\d+$") && outField2.getText().matches("^-?\\d+$")){
  	 				int x1 = Integer.parseInt(inField.getText());  
  	 				int y1 = Integer.parseInt(outField.getText());  
  	 				int x2 = Integer.parseInt(inField2.getText());  
  	 				int y2 = Integer.parseInt(outField2.getText());  
  	 				int width = Math.abs(x1 - x2);
  	 				int height = Math.abs(y1 - y2);
  	 				ans.setText("Answer:   " + (width*height) + " units squared");
  				   }else{
  					 JOptionPane.showMessageDialog(frame, "The coordinates must be integers! (ex: 21 43 1 176)");
  				   }
  				}
  		   }
     	});
    	
    	buttonPanel.add(calcButton);
    	frame.setVisible(true);
	}
	
}



