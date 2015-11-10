import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
public class TicTacToe {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		Player p1 = new Player();
		Player p2 = new Player();
		Input input = new Input();
		String symbol = "X";
		System.out.println("What is player 1's name?");
		p1.setPlayer(console.nextLine());
		Tabs();
		System.out.println("What is player 2's name?");
		p2.setPlayer(console.nextLine());
		String[][] Table = new String[3][3];
		while(input.isRestart()==true){
			Set<String> slots = new HashSet<String>();
			slots.add("A1");
			slots.add("A2");
			slots.add("A3");
			slots.add("B1");
			slots.add("B2");
			slots.add("B3");
			slots.add("C1");
			slots.add("C2");
			slots.add("C3");
			fillTableWithSpaces(Table);
			input.setInput("");
			input.setPlay(true);
			while(input.isPlay()==true){
				Tabs();
				turn(symbol,Table,p1,p2); //ask for input
				input.setInput(console.nextLine().toUpperCase()); //set input to what they type in (ex: A2)
				if(slots.contains(input.getInput())){
					slots.remove(input.getInput());
					if(symbol.equals("X")){ // if it is player1's turn
						Table[input.returnRow(input.getInput())][input.returnColumn(input.getInput())] = symbol; //put the input into the table at x/y 
						winningStatus(symbol,Table,p1,p2,"X",input,slots,console); //check if player 1 won
						//winningStatus(symbol,Table,p1,p2,"0"); //check if player 2 won
						symbol="O";
					}else if(symbol.equals("O")){// if it is player2's turn
						Table[input.returnRow(input.getInput())][input.returnColumn(input.getInput())] = symbol; 
						//winningStatus(symbol,Table,p1,p2,"X"); //check if player 1 won
						winningStatus(symbol,Table,p1,p2,"O",input,slots,console); //check if player 2 won
						symbol="X";
					}
				}
			}
		}
		console.close();
	}
	
	public static void Tabs(){
		System.out.println("\n\n\n\n\n\n\n");
	}
	
	public static void printTable(String Table[][]){
		System.out.println("\t\t 1  2  3");
		for(int i=0; i<Table.length; i++){
			System.out.print("\t     ");
			if(i==0){
				System.out.print("A");
			}else if(i==1){
				System.out.print("B");
			}else if(i==2){
				System.out.print("C");
			}
			System.out.print("  ");
			for(int x=0; x<Table[0].length; x++){
				System.out.print("["+Table[i][x]+"]");
			}
			System.out.print("\n");
		}
	}
	
	public static void fillTableWithSpaces(String Table[][]){
		for(int i=0; i<Table.length; i++){
			for(int x=0; x<Table[0].length; x++){
				Table[i][x] = " ";
			}
		}
	}
	
	public static void turn(String symbol, String[][] table, Player p1, Player p2){
		System.out.print("\t    ");
		if(symbol.equals("X")){
			System.out.println("*It's " + p1.getPlayer() + "'s turn.*");
		}else if(symbol.equals("O")){
			System.out.println("*It's " + p2.getPlayer() + "'s turn.*");
		}
		System.out.println("Type the row and column you wish to go. (ex: A2)");
		printTable(table);
	}
	
	public static void winningStatus(String symbol, String[][] table, Player p1, Player p2, String checkSymbol, Input input, Set<String> slots,Scanner console){
		String winner = "";
		if(symbol.equals("X")){
			winner = p1.getPlayer();
		}else if(symbol.equals("O")){
			winner = p2.getPlayer();
		}
		//checks rows
		if(table[0][0].equals(checkSymbol) && table[0][1].equals(checkSymbol) && table[0][2].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}else if(table[1][0].equals(checkSymbol) && table[1][1].equals(checkSymbol) && table[1][2].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}else if(table[2][0].equals(checkSymbol) && table[2][1].equals(checkSymbol) && table[2][2].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}
		//checks columns
		else if(table[0][0].equals(checkSymbol) && table[1][0].equals(checkSymbol) && table[2][0].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}else if(table[0][1].equals(checkSymbol) && table[1][1].equals(checkSymbol) && table[2][1].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}else if(table[0][2].equals(checkSymbol) && table[1][2].equals(checkSymbol) && table[2][2].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}
		//checks diagonals
		else if(table[0][0].equals(checkSymbol) && table[1][1].equals(checkSymbol) && table[2][2].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}else if(table[0][2].equals(checkSymbol) && table[1][1].equals(checkSymbol) && table[2][0].equals(checkSymbol)){
			endGame(winner,table,input,console);
		}else if(slots.isEmpty()){
			winner = "404";
			endGame(winner,table,input,console);
		}
	}
	
	public static void endGame(String winner, String[][] table, Input input, Scanner console){
			Tabs();
			if(winner.equals("404")){
				System.out.println("It's a draw!");
			}else{
				System.out.println(winner + " wins!");
			}
			printTable(table);
			System.out.println(" ");
			System.out.println("Would you like to play again? (y/n)");
			String restartOrNot = console.nextLine().toLowerCase();
			if(restartOrNot.equals("y")){
				input.setPlay(false);
				input.setRestart(true);
			}else if(restartOrNot.equals("n")){
				Tabs();
				System.exit(0);
			}	
	}
}