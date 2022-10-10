public class CDoublyLinkedList {

	private class Node {
		private Object data;   //Assume data implemented Comparable
		private Node next, prev;
		private Node(Object data, Node pref, Node next)
		{
			this.data = data;
			this.prev = pref;
			this.next = next;
		}
	}

	private Node head;
	private int size;

	public CDoublyLinkedList() {
		this.head = new Node(null, null, null );
		this.head.next = this.head;
		this.head.prev=this.head;
		this.size = 0;
	}

	public boolean isEmpty() {
		return this.head == this.head.next;
	} 
	
	// Add Object data to start of this LinkedList
	// Please DO NOT change this addFirst() method.
	// You must keep and include this provided addFirst() method
	//      in your source code.
	public void addFirst(Object data) {
		Node nn = new Node(data, this.head, this.head.next);
		this.head.next.prev = nn;
		this.head.next = nn;
		this.size ++;
	}

	// write a method void addLast(Object data) that will insert 
	// the piece of data at the end of the current list.
	// Note: this list is allowed to store null data element in its list node.
	public void addLast(Object data) {
		Node cur = this.head.prev; //end of list
		Node nn = new Node(data, cur, cur.next);
		cur.next = nn;
		this.head.prev = nn;
		this.size++;
	}
	
	// Write the subListOfSmallerValues method.  
	// It should return a CDoublyLinkedList object 
	//     containing data that is smaller than the value passed to the method.
        // If a null data element in this list is encountered, you can skip it.
        // You need to use the CompareTo() method to determine which object is smaller.
        // If list A contains {9, 11, 14, 6, 4, 7} and you call  A.subListOfSmallerValues(10),
        // the method call returns a list that contains data in A that is smaller than 10, the passed-in argument.
        // That is, the returned list contains { 9, 6, 4, 7}.
	public CDoublyLinkedList subListOfSmallerValues(Comparable data) {
		CDoublyLinkedList subList = new CDoublyLinkedList();
		Node cur = this.head.next;
		while(cur != this.head) { //Traverse to end of list
			if (cur.data != null && (data.compareTo(cur.data) > 0)) {
				//cur.data is not null and is less than data passed in
				subList.addLast(cur.data);
			}
			cur = cur.next;
		}
		return subList;
	}
	
	// This method should remove the first occurrence of the data from the list, 
        //      starting at the *BACK* of the list. 
        // It scans the list from back to the front by following the prev links. 
	// The method should return true if successful, false otherwise. 
	// Note that list node may contain null data element. Please handle this edge case.
	public boolean removeStartingAtBack(Object dataToRemove) {
		Node cur = this.head.prev; //end of list
		while (cur != this.head) {
			if (cur.data == null && dataToRemove == null) { //edge case
				cur.prev.next = cur.next;
				cur.next.prev = cur.prev;
				this.size --;
				return true;
			}
			if (cur.data != null && cur.data.equals(dataToRemove)) {
				cur.prev.next = cur.next;
				cur.next.prev = cur.prev;
				this.size --;
				return true;
			}
			cur = cur.prev;
		}
		return false;
	}
	
	// Returns the index of the last occurrence of the specified element in this list, 
	//     or -1 if this list does not contain the element. 
	// More formally, returns the highest index i 
	//     such that (o==null ? get(i)==null : o.equals(get(i))), 
	//     or -1 if there is no such index.
	// Note: a list node may store a null data element. Please handle this edge case.
	public int lastIndexOf(Object o) {
		Node cur = this.head.next;
		int lastIndex = -1, currentIndex = 0;
		while (cur != this.head) {
			if (cur.data == null && o == null) { //edge case
				lastIndex = currentIndex;
			}
			if (cur.data != null && cur.data.equals(o)) {
				lastIndex = currentIndex;
			}
			currentIndex++;
			cur = cur.next;
		}
		return lastIndex; //change this as needed.
	}
	
	
	// Removes from this list all of its elements that 
	//    are NOT contained in the specified linkedlist other.
	// If any element has been removed from this list,
	//    returns true. Otherwise returns false.
	// If other list is null, throws NullPointerException.
        // Helper methods are allowed.
	public boolean retainAll(CDoublyLinkedList other) throws NullPointerException {
		if (other.isEmpty()) {
			throw new NullPointerException("Other list is empty!");
		}
		boolean deletedSomething = false;
		Node cur = this.head.next;

		while (cur != this.head) {
			//In the while loop, every node from this list is compared to every data node from the other list, and
				//if the data from each node equal each other, the node from this list is kept; otherwise, it is deleted
			boolean keepNode = false;
			for (Node otherCur = other.head.next; otherCur != other.head; otherCur = otherCur.next) {
				if (cur.data == null && otherCur.data == null ) {
					keepNode = true;
				}else if (cur.data != null && cur.data.equals(otherCur.data)) {
					keepNode = true;
				}
			}
			if (!keepNode) { //cur.data does not match any of the data in other list
					cur.prev.next = cur.next;
					cur.next.prev = cur.prev;
					this.size --;
					deletedSomething = true;
			}
			cur = cur.next;
		}
	    return deletedSomething;
	}
	

        // Write this method to sort this list using insertion sort algorithm, 
        //      as we have learned in the classroom.
	public void insertionSort() { //Does not work for lists containing null data
		Node sortedCur = this.head.next, unsortedCur = this.head.next.next;
		while (unsortedCur != this.head) {
			while ( ((Comparable) unsortedCur.data).compareTo(sortedCur.data) < 0 && sortedCur != this.head ) {
				//Continue walking sortedCur back until sortedCur is either smaller or is the head node
				sortedCur = sortedCur.prev;
			}
			//cut unsortedCur and insert it in front of sortedCur
			unsortedCur.prev.next = unsortedCur.next;
			unsortedCur.next.prev = unsortedCur.prev;
			Node nn = new Node(unsortedCur.data, sortedCur, sortedCur.next);
			sortedCur.next.prev = nn;
			sortedCur.next = nn;

			//move unsortedCur to the next First Unsorted Data, and move sortedCur to the left of FUD
			unsortedCur = unsortedCur.next;
			sortedCur = unsortedCur.prev;
		}
		
	}
	
	@Override
	public String toString() {
		String result = "{";
	    for (Node node = this.head.next; node != this.head; node = node.next) {
	    		if(node.next != this.head)
	    			result += node.data + "->"; 
	    		else
	    			result += node.data;
	    }
	    return result + "}";
	  }
}
			