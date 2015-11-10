/** Demonstrates the time it takes to retrieve from a Linked List vs. Array List
 ** @author Khalob Cognata*/
import java.math.BigDecimal;

public class Driver {
	public static void main(String[] args) {
		List l = new List(); //linked list
		ArrayListClass ar = new ArrayListClass();
		int n; //size of the array
		int numberOfRetrievals = 1000;
		BigDecimal totalTime;
		BigDecimal NR;
		
		System.out.println("Data structure\tn\ttotal time\taverage time");
		
		//Linked List: --------------------------------------------------------------------------
		//Test 1
		n = 1000;
		NR = new BigDecimal(numberOfRetrievals);
		l.fillRandom(n); //fill the linked list with the size of n, with random numbers up to n-1
		totalTime = l.retrieveRandom(n, numberOfRetrievals);
		System.out.println("Linked List\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//Test 2
		n = 10000;
		NR = new BigDecimal(numberOfRetrievals);
		l.fillRandom(n); //fill the linked list with the size of n, with random numbers up to n-1
		totalTime = l.retrieveRandom(n, numberOfRetrievals);
		System.out.println("Linked List\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//Test 3
		n = 100000;
		NR = new BigDecimal(numberOfRetrievals);
		l.fillRandom(n); //fill the linked list with the size of n, with random numbers up to n-1
		totalTime = l.retrieveRandom(n, numberOfRetrievals);
		System.out.println("Linked List\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//Test 4
		n = 1000000;
		NR = new BigDecimal(numberOfRetrievals);
		l.fillRandom(n); //fill the linked list with the size of n, with random numbers up to n-1
		totalTime = l.retrieveRandom(n, numberOfRetrievals);
		System.out.println("Linked List\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//End of Linked List
		
		//ArrayList: -----------------------------------------------------------------------------
		//Test 1
		n = 1000;
		NR = new BigDecimal(numberOfRetrievals);
		ar.fillRandom(n);
		totalTime = ar.retrieveRandom(n, numberOfRetrievals);
		System.out.println("ArrayList\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//Test 2
		n = 10000;
		NR = new BigDecimal(numberOfRetrievals);
		ar.fillRandom(n);
		totalTime = ar.retrieveRandom(n, numberOfRetrievals);
		System.out.println("ArrayList\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//Test 3
		n = 100000;
		NR = new BigDecimal(numberOfRetrievals);
		ar.fillRandom(n);
		totalTime = ar.retrieveRandom(n, numberOfRetrievals);
		System.out.println("ArrayList\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//Test 4
		n = 1000000;
		NR = new BigDecimal(numberOfRetrievals);
		ar.fillRandom(n);
		totalTime = ar.retrieveRandom(n, numberOfRetrievals);
		System.out.println("ArrayList\t" + n + "\t" + totalTime + "\t\t" + totalTime.divide(NR));
		
		//End of ArrayList
	}
}
