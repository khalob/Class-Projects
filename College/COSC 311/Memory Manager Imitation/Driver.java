 /** Handles user interactions for MemoryManager
  * @author Khalob Cognata*/


import java.util.Scanner;
public class Driver {
	public static void main(String[] args) throws NumberFormatException, Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("How large is the memory pool?");
		int x = input.nextInt();
		MemoryManager prog = new MemoryManager(x);
		String s = "";
		System.out.println("Your commands are as follows: \nalloc(int) \nfree(int) \nprint \nend\n");
		
		prog.print();
		while(!s.equals("end")){
			s = input.nextLine().toLowerCase();
			if(s.contains("alloc")){
				s = s.replaceAll("\\D+","");
				prog.alloc(Integer.parseInt(s));
				prog.print();
			}else if(s.contains("free")){
				s = s.replaceAll("\\D+","");
				prog.free(Integer.parseInt(s));
				prog.print();
			}else if(s.equals("print")){
				prog.print();
			}
		}
		input.close();
	}
}
