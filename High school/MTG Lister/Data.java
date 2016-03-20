import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Data {
	static Scanner console = new Scanner(System.in);
	static RunThis object = new RunThis();
	String file;
	String input;
	
//---------------------USERNAME------------------------------------------//
	@SuppressWarnings("resource")
	public String readUserName(){
		String fileName="username.txt";
		Scanner inputStream = null;
		//read
		try{
			inputStream = new Scanner(new File(fileName)); //try to open the file
		}
		catch(Exception e){
			GUI.changeUsername();
		}
		return inputStream.nextLine();
	}
	
	public void writeUsername(String user){
		String fileName="username.txt"; //file name of file being read
		PrintWriter outputStream = null;
		File outFile = new File(fileName);
		try{
			FileOutputStream fos = new FileOutputStream(outFile);
        	outputStream = new PrintWriter(fos);
		}
		catch(Exception e){
				System.out.println("Could not write to the file named "+ fileName); // if it doesn't find it, tell them
				System.exit(0);  // and then exit.
    	}
		outputStream.print(user);
		outputStream.close();
	}
	
	
//---------------------LISTS------------------------------------------//
		public static void listWrite(String line, String fileName){
			boolean wrote = false, substringed=false;
			PrintWriter outputStream2 = null;
			Scanner inputStream2 = null;
			//listRemove("2x Pie","Yourdecklist.txt",0);
			//write and read
			try{
				outputStream2 = new PrintWriter(new FileOutputStream(fileName,true)); //try to create the file
				inputStream2 = new Scanner(new File(fileName)); //try to open the file
			}
			catch(Exception e){
				System.out.println("Could not create the file "+ fileName); // if it doesn't find it, tell them
				System.exit(0);  // and then exit.
			}
			int newAmount = 0;
			String replacingLine="",lineBeforeSubstring="";
			line = line.trim();
			if(line.contains(" ")){
				String[] parts = line.split(" ");
				line = "";
				for(int i=0; i<parts.length;i++){
					line = line + parts[i].substring(0,1).toUpperCase() + parts[i].substring(1,parts[i].length()) +" ";
				}
			}else{
				line = line.substring(0,1).toUpperCase() + line.substring(1,line.length());
			}
			
			if(!line.matches(".*\\d.*") && substringed==false){ //if it doesn't contain a number
				System.out.println("CHANGING TO 1x!");
				line = "1x " + line; 
			}
			
			if(line.matches(".*\\d.*")){ //if it contains an int
				System.out.println("SHOULD SEE THIS MESSAGE");
				newAmount = Integer.parseInt(line.replaceAll("[\\D]", "")); //we set newAmount to that int
				lineBeforeSubstring = line; //save the string before changing it
				line = line.substring(3, line.length()); //remove the number and "x" (ex: "4x snap" turns into "snap")
				substringed = true; //show that we've taken a substring
			}
			
			int amount =0;
			while(inputStream2.hasNextLine()){
				String currentLine = inputStream2.nextLine().trim();
				System.out.println("Does:" + currentLine.toLowerCase()+"   contain:"+ line.toLowerCase());
				if(currentLine.toLowerCase().trim().contains(line.toLowerCase().trim()) && line.length() >= ((currentLine.length()-3)/2)){
				//if(currentLine.toLowerCase().trim().equals(line.toLowerCase().trim())){     //uncomment this instead of the above line if you dont want auto correct for user input
					System.out.println("Got HERE");
					replacingLine = currentLine;
					System.out.println(replacingLine);
					wrote = true;
					break;
				}
			}
			
			if(wrote == false){
				if(substringed==true){
					line = lineBeforeSubstring;
				}
				outputStream2.println(line);				
			}

			if(!replacingLine.equals("")){ //if replacing line is not empty
				System.out.println("replacing line value: " + Integer.parseInt(replacingLine.replaceAll("[\\D]", "")));
				System.out.println("newamount: " + newAmount);
				amount = (Integer.parseInt(replacingLine.replaceAll("[\\D]", ""))) + newAmount; //add the old string's amount to the new string's amount
				System.out.println("amount: "+ amount + "x "+ replacingLine.substring(3, replacingLine.length()));
				outputStream2.println(amount + "x "+ replacingLine.substring(3, replacingLine.length()));
				outputStream2.close();
				inputStream2.close();
				System.out.println("removing line: " + replacingLine);
				listRemove(replacingLine,fileName,0); //removes one from the 2nd place too
			}else{
				outputStream2.close();
				inputStream2.close();
			}
			
		}
		
		
		public void listRead(String fileName){
			String type = fileName.replace("List.txt", "");
			Scanner inputStream = null;
			//read
			try{
				inputStream = new Scanner(new File(fileName)); //try to open the file
			}
			catch(Exception e){
				System.out.println("There is not a list for "+type+" cards.");
				System.out.println("\n~Press enter to return to the main menu~");
				console.nextLine();
				object.runMain();
			}
			int count=0;
			while(inputStream.hasNextLine()){
				/*count++;
				if(count>12){
					count=0;
					System.out.println();
				}*/
				System.out.println(inputStream.nextLine() + "\t");
			}
			inputStream.close();
		}
		
		
		public static void listRemove(String lineToRemove, String fileName, int amount){
			String type = fileName.replace("List.txt", "");
			Scanner inputStream = null;
			PrintWriter outputStream = null;
			File inputFile = new File(fileName);
			File outFile = new File("temp.txt");
			try{
				inputStream = new Scanner(new File(fileName)); //try to open the file
				FileOutputStream fos = new FileOutputStream(outFile);
	        	outputStream = new PrintWriter(fos);
			}
			catch(Exception e){
				System.out.println("There is not a list for "+type+" cards.");
				object.runMain();
	    	}
			int amountToRemove=0;
			if(lineToRemove.matches(".*\\d.*")){ //contains a int
				amountToRemove = Integer.parseInt(lineToRemove.replaceAll("[\\D]", ""));
				lineToRemove = lineToRemove.substring(3,lineToRemove.length());
			}
			boolean found = false;
			while(inputStream.hasNextLine()){
				String currentLine = inputStream.nextLine().trim();
				if(currentLine.toLowerCase().contains(lineToRemove.toLowerCase().trim()) && lineToRemove.length() >= (currentLine.length()/2)-1 && found == false){
					found = true;
					if(amount!=0){
						currentLine = currentLine.substring(3, currentLine.length());
						outputStream.println(amount + "x " + currentLine); // ex: 4x 
					}else if(currentLine.matches(".*\\d.*")){
						if(Integer.parseInt(currentLine.replaceAll("[\\D]", "")) < amountToRemove){
							System.out.println("You do not have that many cards of that name.");
							outputStream.println(currentLine);
						}else if(Integer.parseInt(currentLine.replaceAll("[\\D]", "")) > amountToRemove){
							int newAmount = Integer.parseInt(currentLine.replaceAll("[\\D]", "")) - amountToRemove;
							currentLine = currentLine.substring(3, currentLine.length());
							outputStream.println(newAmount +"x "+ currentLine);
						}
					}
				}else{
					outputStream.println(currentLine);
				}
			}
			inputStream.close();
			outputStream.close();
			if(inputFile.delete()){ //deletes the old file
				//System.out.println(fileName+" deleted");   
				outFile.renameTo(inputFile); // then renames the temp file to the old (now deleted) file.
			}
		}

}
