import java.math.BigDecimal;
import java.util.Random;


public class List {
	private static Node head; // Reference to current cursor position
	private static Node cursor;
	private static Random ran = new Random();
	 
	    List (){                  // Default constructor: Creates an empty list
	    	head = null;
	    	cursor = null;
	    }
	    
		void print(){
			Node pointer = head;
			while(pointer != null){
				System.out.println(pointer.data);
				pointer = pointer.next;
			}
			if(head == null)
				System.out.println("empty");	
		}
		
		/** Fills the list with random values ranging from 0 to n-1*/
		void fillRandom(int n){ // n = the length of the linked list
			head = new Node(ran.nextInt(n));
			Node pointer = head;
			int num = n;
			while(num > 0){
				pointer.next = new Node(ran.nextInt(n-1));
				num--;
				pointer = pointer.next;
			}
		}
		
		/** Returns the time it takes to find x values in the list*/
		BigDecimal retrieveRandom(int n, int num){ // n = the length of the linked list and num = number of retrievals
			Node pointer;
			int random;
			BigDecimal startTime = new BigDecimal(System.currentTimeMillis());
			while(num>0){
				pointer = head;
				random = ran.nextInt(n-1);
				while(pointer != null){
					if(pointer.data == random){
						break;
					}
					pointer = pointer.next;
				}
				num--;
				}
			BigDecimal elapsedTime = new BigDecimal(System.currentTimeMillis());
			return elapsedTime.subtract(startTime);
			}
}
