/** Imitates the use of a memory manager using two linked lists*/
public class MemoryManager {
	int arrayLength = 0;
	static Node usedList = null;
	static Node freeList = null;
	static String s = "";
	
	public MemoryManager(int size){
		Node arr[] = new Node[size];
		arrayLength = size;
		//create free list
		for(int x=0; x<arr.length; x++){
			arr[x] = new Node();
			arr[x].data = x;
			if(x != 0)
				arr[x-1].free = arr[x];
		}

		//create free list
		freeList = arr[0];
	}
	
	//prints the lists of free and used storage locations
	void print(){
		System.out.print("Free list: ");
		if(freeList == null)
			System.out.print("empty");	
		Node pointer = freeList;
		while(pointer != null){
			System.out.print(pointer.data);
			if(pointer.free != null)
				System.out.print(",");
			pointer = pointer.free;
		}
		
		System.out.print("\nUsed list: ");
		if(usedList == null)
			System.out.print("empty");	
		Node usedPointer = usedList;
		while(usedPointer != null){
			System.out.print(usedPointer.data);
			if(usedPointer.next != null)
				System.out.print(",");
			usedPointer = usedPointer.next;
		}
		System.out.println();
	}

	
	int alloc(int i) throws Exception{
		if(freeList == null)
			throw new Exception("Block Not Available: there is a insufficient amount of free blocks."); 
		int index = 0;
		index = freeList.data;	//stores data into local variable
		freeList.next = usedList; //links the current node's next to the head of the usedList
		usedList = freeList;	//makes the current node the new head of the usedList
		freeList = usedList.free; //iterates freeList to the next free node
		usedList.free = null; //removes the old link for free pointer
		return index;
	}
	
	void free(int i) throws Exception{
		if(i > arrayLength){
			throw new Exception("Invalid Adress Exception: "+ i + " is not within the memory's bounds!"); 
		}
		Node f = freeList;
		while(f != null){
			if(f.data == i)
				throw new Exception("Invalid Address: "+ i + " is already free!");
			f = f.next;
		}
		Node p = usedList;
		Node q = p;
		int count = 0;
		while(p != null){
			if(p.data == i && count ==0){
				usedList.free = freeList; //similar to alloc, but adding to freelist and taking from used
				freeList = usedList;
				usedList = usedList.next;
				freeList.next = null;
				return;
			}else if(p.data == i && count !=0){
				q.next = p.next; //links the previous node to the one p is pointing at so that we dont break the list
				p.free = freeList;
				freeList = p;
				freeList.next = null;
				return;
			}
			q = p;
			p = p.next;
			count++;
		} 
	}
}

 	class Node {
 		int data;
 		Node next;
 		Node free;
 	}