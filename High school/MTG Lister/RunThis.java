import java.util.*;

import javax.swing.JLabel;

public class RunThis {
	public static void main(String[] args) {
		Data data = new Data();
		String username="";
		username = data.readUserName();
		System.out.println("\t\tWelcome "+ username + "! What would you like to do?"); //welcome user
		printOptions();//print options
		sortMenuChoice(gatherInput(data), data);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void printOptions(){
		System.out.println("Type the number of the action to execute it. (Ex: Type '2' to view your collection.)");
		System.out.println("1) Add/Remove Items\n2) View your collection\n3) View borrowed cards\n4) View lent cards\n5) Change username\n6) Exit program");
	}
	
	public static int gatherInput(Data data){
		//Scanner console = new Scanner(System.in); //create scanner
		Set<String> optionsList = new HashSet<String>();
		optionsList.add("1");
		optionsList.add("2");
		optionsList.add("3");
		optionsList.add("4");
		optionsList.add("5");
		optionsList.add("6");
		String optionChosen = data.console.nextLine();
		if(optionsList.contains(optionChosen)){
			//data.console.close();
			return Integer.parseInt(optionChosen);
		}else{
			System.out.println("Sorry that input was incorrect. Try typing an integer such as 1, 2, or 3.");
			printOptions();//print options
			return gatherInput(data);
		}
	}
	
	public static void Tabs(){
		System.out.println("\n\n\n\n\n\n\n");
	}
	
	public static void sortMenuChoice(int value, Data data){
		switch(value){
			case 1:
				case1(data);
				//execute add/remove
				break;
			case 2:
				Tabs();
				Tabs();
				System.out.println("_________Your deck list_________");
				data.listRead("yourList.txt");
				System.out.println("\n~Press enter to return to the main menu~");
				data.console.nextLine();
				runMain();
				break;
			case 3:
				Tabs();
				Tabs();
				System.out.println("_________Borrowed deck list_________");
				data.listRead("borrowedList.txt");
				System.out.println("\n~Press enter to return to the main menu~");
				data.console.nextLine();
				runMain();
				break;
			case 4:
				Tabs();
				Tabs();
				System.out.println("_________Lent deck list_________");
				data.listRead("lentList.txt");
				System.out.println("\n~Press enter to return to the main menu~");
				data.console.nextLine();
				runMain();
				break;
			case 5:
				System.out.println("What would you like your new username to be?");
				String name = data.console.nextLine();
				data.writeUsername(name);
				runMain();
				break;
			case 6:
				System.exit(0);
				break;
		}
	}
	
	public static void case1(Data data){
		Tabs();
		System.out.println("Which list would you like to modify? (Ex: Type '2' to change your borrowed list.)");
		System.out.println("1) Your cards list\n2) Borrowed cards list\n3) Lent cards list");
		
		//Scanner console = new Scanner(System.in); //create scanner
		Set<String> optionsList = new HashSet<String>();
		optionsList.add("1");
		optionsList.add("2");
		optionsList.add("3");
		String optionChosen = data.console.nextLine();
		if(optionsList.contains(optionChosen)){
			if(optionChosen.equals("1")){
				yourList(data);
			}else if(optionChosen.equals("2")){
				borrowedList(data);
			}else if(optionChosen.equals("3")){
				lentList(data);
			}
		}else{
			System.out.println("Sorry that input was incorrect. Try typing an integer such as 1 or 2");
			case1(data);
		}
	}
	
	public static int addOrRemove(Data data){
		Tabs();
		System.out.println("Would you like to add or remove? (Ex: Type '2' to remove items.)");
		System.out.println("1) Add\n2) Remove");
		
		//Scanner console = new Scanner(System.in); //create scanner
		Set<String> optionsList = new HashSet<String>();
		optionsList.add("1");
		optionsList.add("2");
		String optionChosen = data.console.nextLine();
		if(optionsList.contains(optionChosen)){
			//data.console.close();
			return Integer.parseInt(optionChosen);
			//add();
		}else{
			System.out.println("Sorry that input was incorrect. Try typing an integer such as 1 or 2");
			return addOrRemove(data);
		}
	}
	
	public static void yourList(Data data){
		int addorNot = addOrRemove(data);
		data.file = "yourList.txt";
		if(addorNot == 1){ //adding
			adding(data);
		}else if(addorNot == 2){ //removing
			removing(data);
		}
	}
	
	public static void borrowedList(Data data){
		int addorNot = addOrRemove(data);
		data.file = "borrowedList.txt";
		if(addorNot == 1){ //adding
			adding(data);
		}else if(addorNot == 2){ //removing
			removing(data);
		}
	}
	public static void lentList(Data data){
		int addorNot = addOrRemove(data);
		data.file = "lentList.txt";
		if(addorNot == 1){ //adding
			adding(data);
		}else if(addorNot == 2){ //removing
			removing(data);
		}
	}

	public static void adding(Data data){
		System.out.println("What would you like to add to the list? (Include the amount Ex: 4x snapcaster) \n\tType \"nothing\" or leave the input blank to return to the main menu.");
		String input = data.console.nextLine().toLowerCase();
		if(input.toLowerCase().equals("nothing") || input.equals("")){
			runMain();
		}else{
			data.listWrite(input, data.file);
			adding(data);
		}
	}
	
	public static void removing(Data data){
		Tabs();
		Tabs();
		System.out.println("_________Your deck list_________");
		data.listRead(data.file);
		System.out.println();
		System.out.println("What would you like to remove from the list? (Include the amount Ex: 4x snapcaster) \n\tType \"nothing\" or leave the input blank to return to the main menu.");
		String input = data.console.nextLine().toLowerCase();
		if(input.equals("nothing") || input.equals("")){
			runMain();
		}else{
			if(!input.matches(".*\\d.*")){
				input = "1x " + input; 
			}
			data.listRemove(input, data.file,0);
			removing(data);
		}
	}
	
	public static void runMain(){
		String[] args = null;
		Tabs();
		main(args);
	}	
	
	public static void printToGUI(String string){
		JLabel label = new JLabel(string);
	}
}