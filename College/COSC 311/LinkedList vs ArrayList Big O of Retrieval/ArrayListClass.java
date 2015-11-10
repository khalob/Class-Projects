import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class ArrayListClass {
	ArrayList<Integer> arr = new ArrayList<Integer>();
	private static Random ran = new Random();
	
	
	/** Fills the list with random values ranging from 0 to n-1*/
	void fillRandom(int n){// n = the length of the arrayList
		int num = n;
		while(num>0){
			arr.add(ran.nextInt(n-1));
			num--;
		}
	}
	
	/** Returns the time it takes to find x values in the list*/
	BigDecimal retrieveRandom(int n, int num){ // n = the length of the arrayList and num = number of retrievals
		BigDecimal startTime = new BigDecimal(System.currentTimeMillis());
		while(num>0){
			arr.contains(ran.nextInt(n-1));
			num--;
		}
		BigDecimal elapsedTime = new BigDecimal(System.currentTimeMillis());
		return elapsedTime.subtract(startTime);
	}
}
