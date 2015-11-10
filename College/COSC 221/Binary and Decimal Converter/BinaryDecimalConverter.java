import java.util.Scanner;

/**
* The BinaryDecimalConverter program implements an application that
* simply converts user inputed decimal or binary numbers to the 
* following types: Signed Magnitude, 1's Complement Notation, 2's 
* Complement Notation, and Excess-512 Notation. Limited to 10-bits.
* 
* 
* @author  Khalob Cognata
* @version 1.0
* @since   2015-10-07
*/

public class BinaryDecimalConverter {
	//new object of Scanner class that will be used to gather the user's input
	static Scanner in = new Scanner(System.in); 
	
	static String arr[] = new String[5]; //new array that will hold each type of conversion as follows:
	//arr[0] = userInput | arr[1] = SignMagnitude | arr[2] = 1's Comp | arr[3] = 2's Comp | arr[4] = Excess-512
	
	public static void main(String[] args) {
		String type; //A string that holds the type of input the user will use (binary or decimal)
		
		//prompt the user with a message
		System.out.println("Hello, would you like to convert from binary or decimal? Type \"b\" for binary or \"d\" for decimal.");
		//store input
		type = in.nextLine();
		//send that string to the getTypeFromUser method
		getTypeFromUser(type);

		//print results
		System.out.print("\n\nInput: "+ arr[0]+"\nSign-Magnitude: "+arr[1]+ "\nOne's Complement: " + arr[2] + "\nTwo's Complement: "+ arr[3] + "\nExcess-512 Notation: " + arr[4]);
	}//end of main
	
	/** Prompts the user for binary or decimal coversion */
	public static void getTypeFromUser(String type){
		String typeToLower = type.toLowerCase();
		if(typeToLower.equals("b")){ //check if input is "b" or "d"
			arr[0] = getInputBinary(); //store user input into our array
			binary(arr[0]);
			
		}else if(typeToLower.equals("d")){ //choose method based on user's input
			arr[0] = getInputDecimal(); //store user input into our array
			decimal(arr[0]);
		}else{
			//prompt the user with a message to show them they do not have a valid input
			System.out.println("Sorry, " + type + " is not a valid input...");
			//prompt the user again with the options they have to choose from 
			System.out.println("Would you like to convert FROM binary or decimal? Type \"b\" for binary or \"d\" for decimal.");
			//send their input to the getTypeFromUser once more
			getTypeFromUser(in.nextLine());
		}
	}//end of getTypeFromUser
	
	/**Prompts the user for input until valid input is found for the decimal type*/
	private static String getInputDecimal() {
		String input;
		boolean validInput = false;
		boolean validSize = false;
		do{
			System.out.println("What is the decimal number that would you like to convert?"); //prompts the user to enter their input
			input = in.nextLine(); //set the value of our variable to the user's input
			
			if(input.matches("[0-9]+") || input.startsWith("-") && input.substring(1,input.length()).matches("[0-9]+")){
				validInput = true;
				if(Integer.parseInt(input) < 512 && Integer.parseInt(input) >= -512){
					validSize = true;
				}else{
					validInput = false;
					System.out.println("Sorry that is not a valid decimal number... The number range is 511 to -512."); //tells the user that their input is invalid
				}
			}else{
				System.out.println("Sorry that is not a valid decimal number... Do not use letters or symbols."); //tells the user that their input is invalid
			}
			
		}while(!validInput || !validSize); //while input is incorrect or not the correct size, do the above.
		return input;
	}

	/**Prompts the user for input until valid input is found for the binary type*/
	public static String getInputBinary(){
		String input;
		boolean validInput = false;
		boolean validSize = false;
		do{
			System.out.println("What is the binary number that would you like to convert?"); //prompts the user to enter their input
			input = in.nextLine(); //set the value of our variable to the user's input
			if(input.replaceAll("0", "").replaceAll("1","").length() == 0){ //returns true if the string is made of only 0s and 1s
				validInput = true;
				if(input.length() == 10){ //returns true if the string is made of only 0s and 1s
					validSize = true;
				}else{
					validInput = false;
					System.out.println("Sorry that is not a valid binary number... It must be a 10-bit string."); //tells the user that their input is invalid
				}
			}else{
				System.out.println("Sorry that is not a valid binary number... Only write 0s or 1s."); //tells the user that their input is invalid
			}
		} while(!validInput || !validSize); //while input is false, do the above.	
		return input;
	}
	
	/** Converts from binary to Signed Magnitude, 1's Complement Notation, 2's Complement
	 * Notation and Excess-512 Notation. */
	public static void binary(String inputAsString){
		//calculate conversions
		if(inputAsString.startsWith("1")){ //this means it is a negative number and we need to apply different rules
			arr[1] =  findNegSignMag(inputAsString);
			arr[2] = findNegSignMag(findNegOnesComAsBinary(inputAsString));
			arr[3] = ""+ (Integer.parseInt(arr[2]) - 1);
			arr[4] = "" + (Integer.parseInt(arr[3]) + 512);
		}else{
			arr[1] =  findPosSignMag(inputAsString);
			arr[2] = arr[1]; //1's Complement and Sign-Magnitude are the same result when 1's complement is a positive number
			arr[3] = arr[2]; //2's Complement is also the same when positive
			arr[4] = "" + (Integer.parseInt(arr[3]) - 512);
		}
		
	}
	
	/** Finds the Sign-Magnitude returning the decimal value with a negative sign. */
	public static String findNegSignMag(String input){
		//remove the first number, then send it to findPosSignMag. When it comes back, add a negative sign to it
		return "-" + findPosSignMag(input.substring(1, input.length())); //because you removed the first number that showed it was negative.
	}
	
	/** Finds the SignMagnitude from a binary number and returns it as a decimal. */
	public static String findPosSignMag(String input){
		int result = 0;
		
		//each time we are chipping the end off of a string and adding 2^n each time to our
		// result variable (n = the amount of characters we take off the end of the string)
		for(int i =0; input.length()>0; i++){
			if(input.endsWith("1")){
				result += Math.pow(2, i);
			}
			input = input.substring(0, input.length()-1);
		}
		
		return "" + result;
	}
	
	/** Changes a binary string from Signed-Magnitude to 1's Complement Notation. */
	public static String findNegOnesComAsBinary(String input){
		//return the following "firstDigitOfInput" + the other digits when converted back into sign mag)
		return input.substring(0,1) + input.substring(1, input.length()).replaceAll("0", "x").replaceAll("1", "0").replaceAll("x", "1");
	}
	
	/** Converts a binary string from 1's Complement Notation to 2's Complement Notation. */
	public static String findNegTwosComAsBinary(String onesCom){
		if(onesCom.endsWith("0")){
			return onesCom.substring(0, onesCom.length()-1) + "1";
		}
		
		boolean hasCarry = true;
		String temp = onesCom.substring(1,onesCom.length());
		String result = "";
		
		//we keep chipping off the end of a string and add a 1 or 0 to our result string 
		//depending on if we have a carry from the last add and what we are adding to
		while(temp.length()>0){
			if(temp.endsWith("0")){
				if(hasCarry){
					temp = temp.substring(0, temp.length()-1);
					result = "1" + result;
					hasCarry = false;
				}else{
					temp = temp.substring(0, temp.length()-1);
					result = "0" + result;
				}
			}else{
				if(hasCarry){
					temp = temp.substring(0, temp.length()-1);
					result = "0" + result;
					hasCarry = true;
				}else{
					temp = temp.substring(0, temp.length()-1);
					result = "1" + result;
				}
			}
		}
		
		return "1" + result;
	}
	
	/** Converts from decimal to Signed Magnitude, 1's Complement Notation, 2's Complement
	 * Notation and Excess-512 Notation. */
	public static void decimal(String inputAsString){
		String decimalNumberAsBinary = "";
		int inputAsInteger = 0;
		boolean isNegative = false;
		
		if(inputAsString.contains("-") || inputAsString.contains("-")){ //contains a negative symbol
			inputAsString = inputAsString.replace("-", "").replace("-", ""); //remove either type of symbol from the string
			isNegative = true;	//change the boolean to reflect that the number is negative (we use this later)
		}
		
		 //change the string value to an integer and place that value in the variable named "inputAsInteger"
		inputAsInteger = Integer.parseInt(inputAsString);
		
		//convert the input to binary
		int lowestDivisablePower = 0;
		
		for(int i=9; i>0; i--){ //powers of 0-9 because we have up to 10 bit input values
			if(Math.pow(2, i) < inputAsInteger){
				lowestDivisablePower = i;
				break;
			}
		}
		
		//adds the appropriate number of zeros so that it will fit the 10-bit format.
		for(int i=1; i< (10-lowestDivisablePower); i++){ 
			decimalNumberAsBinary += "0"; 
		}

		decimalNumberAsBinary += recursionDecimal(inputAsInteger, lowestDivisablePower);
		
		//convert for each type 
		if(isNegative){
			//remove the first digit and add on a 1 to show it is negative
			arr[1] = "1" + decimalNumberAsBinary.substring(1,decimalNumberAsBinary.length()); 
			arr[2] = findNegOnesComAsBinary(arr[1]);
			arr[3] = findNegTwosComAsBinary(arr[2]);
			arr[4] = "0" + arr[3].substring(1, arr[3].length()); //excess is the same as 2's comp, but the sign is flipped
		}else{
			//remove the first digit and add on a 1 to show it is positive
			arr[1] = "0" + decimalNumberAsBinary.substring(1,decimalNumberAsBinary.length());
			arr[2] = arr[1];//1's Complement and Sign-Magnitude are the same result when 1's complement is a positive number
			arr[3] = arr[2];//2's Complement is also the same when positive
			arr[4] = "1" + arr[3].substring(1, arr[3].length()); //excess is the same as 2's comp, but the sign is flipped
		}
	}
		
	/** Converts a decimal into a string of binary */
	public static String recursionDecimal(int dec, int pow){ //uses recursion to create binary from decimal
		int decToPow = (int) Math.pow(2, pow);
		if(pow == 0){
			if(dec==1){
				return "1";
			}
			return "0";
		}
		
		if(Math.pow(2, pow) <= dec){
			return "1" + recursionDecimal(dec - decToPow, pow-1) ;
		}
		return "0" + recursionDecimal(dec, pow-1);
	}
	
}//end of class
