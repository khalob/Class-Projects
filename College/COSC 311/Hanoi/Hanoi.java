/*@Khalob Cognata
Tower of Hanoi: Follows the rules and guidelines of the game named Tower of Hanoi.
This program uses the recursion method to solve this problem. 
*/
import java.util.Scanner;


public class Hanoi {
	
	public static void main(String[] args) {
			Scanner in = new Scanner(System.in);
			
			System.out.println("How many disks should be placed on the left most post?");
			int leftPosts = in.nextInt();
			
			printHanoi(leftPosts,1,3,2,leftPosts);
	}
	

	
	//move top to the right most, then move the left to the middle, then move the right on top
	public static void printHanoi(int size, int from, int spare, int to, int ID){ 
		if(size==0){//base case, when we run out of disks at the left most post, we return, thus ending the recursion.
			return;//ends the recursion
		}//otherwise
		
		//declare two characters to show the source and destination when we later print it out.
		char cFrom = ' ';
		char cTo = ' ';
		
		//uses the function that takes 1,2,3 and turns them into A,B,C accordingly.
		cFrom = postName(from);
		cTo = postName(to);
		
		//It takes three steps for each set of two disks.
		printHanoi(size-1, from, to, spare, ID-1); //Print a disk moving to the spare aka open spot.
		System.out.println("Move " + ID +  " from " +cFrom+ " to " +cTo); //Print another disk moving to the middle post from the left-most post.
		printHanoi(size-1, spare, from, to, ID-1); //finally, print the first moved disk as it goes to the middle post.
	}
	
	public static char postName(int postNum){
		if(postNum == 1){
			return 'A';
		}else if(postNum == 2){
			return 'B';
		}else if(postNum == 3){
			return 'C';
		}
		
		return 'Z'; //error should not happen ever.
	}
}