//Brendan Kendall
//CSCD 300
//10/10/2022

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


	public void addLast(Object data) {
		Node cur = this.head.prev; //end of list
		Node nn = new Node(data, cur, cur.next);
		cur.next = nn;
		this.head.prev = nn;
		this.size++;
	}
	

	public CDoublyLinkedList subListOfSmallerValues(Comparable data) {
		CDoublyLinkedList subList = new CDoublyLinkedList();
		Node cur = this.head.next;
		while(cur != this.head) { //Traverse to end of list
			if (cur.data != null && (data.compareTo(cur.data) > 0)) {
				//cur.data is not null and is less than the data passed in
				subList.addLast(cur.data);
			}
			cur = cur.next;
		}
		return subList;
	}
	

	public boolean removeStartingAtBack(Object dataToRemove) {
		Node cur = this.head.prev; //end of list
		while (cur != this.head) {
			if (cur.data == null && dataToRemove == null) { //handles edge case for null data
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
	

	public int lastIndexOf(Object o) {
		Node cur = this.head.next;
		int lastIndex = -1, currentIndex = 0;
		while (cur != this.head) {
			if (cur.data == null && o == null) { //handles edge case for null data
				lastIndex = currentIndex;
			}
			if (cur.data != null && cur.data.equals(o)) {
				lastIndex = currentIndex;
			}
			currentIndex++;
			cur = cur.next;
		}
		return lastIndex; //returns -1 if object o cannot be found in this list
	}
	

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
				if (cur.data == null && otherCur.data == null ) { //handles edge case for null data
					keepNode = true;
				}else if (cur.data != null && cur.data.equals(otherCur.data)) {
					keepNode = true;
				}
			}
			if (!keepNode) {
				//cur.data does not match any of the data in other list
				cur.prev.next = cur.next;
				cur.next.prev = cur.prev;
				this.size --;
				deletedSomething = true;
			}
			cur = cur.next;
		}
	    return deletedSomething;
	}


	public void insertionSort() { //Does not work for lists containing null data
		//sortedCur starts at the end of the sorted region, and unsortedCur starts at the beginning of unsorted region
		Node sortedCur = this.head.next, unsortedCur = this.head.next.next;
		while (unsortedCur != this.head) {
			while ( sortedCur != this.head && ((Comparable) sortedCur.data).compareTo(unsortedCur.data) > 0 ) {
				//Continue walking sortedCur back until sortedCur is either the head node or is smaller than unsortedCur
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
			