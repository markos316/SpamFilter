/**
 * Priority Queue to store emails according to their spam score
 * Known bugs: none
 * 
 * @author Markos Alemu
 * markos@brandeis.edu
 * May 9, 2019
 * COSI21A PA3
 */
package main;

import java.util.NoSuchElementException;

public class PriorityQueue {

	public Email[] heap; // KEEP THIS PUBLIC
	private int size=0;//size
	private int max = 15;//max amount of spots
	/**
	 * Constructor
	 */
	public PriorityQueue() {
		this.heap=new Email[max];
	}

	// RECOMMENDED method: you can delete it
	/**
	 * Swaps two items in an array
	 * @param i index of first item to be swapped
	 * @param j index of second item to be swapped
	 * @param o array of objects to be swapped
	 * @return
	 */
	private Object[] swap(int i, int j, Object[] o) {
		Object temp = o[i];
		o[i]=o[j];
		o[j]=temp;
		return o;
	}
	/**
	 * Resizes array 
	 */
	private void resize() {
		Email [] e = new Email[this.max*2];
		for (int i = 0; i>this.size;i++) {
			e[i]=this.heap[i];
		}
		this.heap=e;
		this.max=this.max*2;

	}
	// RECOMMENDED method: you can delete it
	/**
	 * Heapifies item down
	 * @param index
	 */
	private void heapifyDown(int index) {
		int l=index*2+1;
		int r=index*2+2;
		int largest=index;

		if(this.heap[l]!=null) {
			//checks if item in left child is greater than index
			if(this.heap[l].getSpamScore()>this.heap[index].getSpamScore()) {
				largest=l;//becomest larges
			}else {
				largest=index;
			}
		}
		if(this.heap[r]!=null) {
			//checks if right child is greater than largest so far
			if(this.heap[r].getSpamScore()>this.heap[largest].getSpamScore()) {
				largest=r;
			}
		}
		//if index is largest, method is over
		if(largest==index) {
			return;
		}else {
			//else swaps
			Email temp = this.heap[index];
			this.heap[index]=this.heap[largest];
			this.heap[largest]=temp;
			this.heapifyDown(largest);
		}
	}

	// RECOMMENDED method: you can delete it
	/**
	 * Moves item up queue
	 * @param index 
	 */
	private void heapifyUp(int index) {
		int parent;
		if(index==0) {
			return;
		}
		if(index%2==1) {
			parent = index/2;
		}else {
			parent = index/2-1;
		}
		if(this.heap[index].getSpamScore()>this.heap[parent].getSpamScore()) {
			Email temp = this.heap[parent];
			this.heap[parent]=this.heap[index];
			this.heap[index]=temp;
			this.heapifyUp(parent);
		}
	}
	/**
	 * Inserts item into priority queue
	 * @param e
	 */
	public void insert(Email e) {
		this.heap[this.size]=e;
		if (this.size>0) {
			this.heapifyUp(this.size);
		}
		this.size++;//checks if needed to be resized
		double check = (double)this.size/(double)this.max;
		if(check>0.7) {
			this.resize();
		}
	}

	/**
	 * Updates spam score
	 * @param eID
	 * @param score
	 */
	public void updateSpamScore(String eID, int score) {
		int i = 0;
		
		while(this.heap[i].getID()!=eID && i<this.size) {
			i++;
		}
		if(i>=this.size) {//checks if index is greater than heap
			throw new NoSuchElementException();
		}else if (this.heap[i].getID().equals(eID)==false) {//or if heap doesn't equal element
			throw new NoSuchElementException();
		}
		else {
			this.heap[i].setSpamScore(score);//sets spam score
			this.heapifyUp(i);//checks if needs to be heapified up or down
			this.heapifyDown(i);
		}
	}
	/**
	 * Gets maximum score
	 * @return score of email at index 0
	 */
	public int getMaximumSpamScore() {
		if(this.heap[0]==null) {
			throw new NoSuchElementException();
		}else {
			return this.heap[0].getSpamScore();
		}
	}
	/**
	 * Deletes maximum item
	 * @return
	 */
	public Email extractMaximum() {
		if(this.heap[0]==null) {//if empty
			throw new NoSuchElementException();
		}
		if(this.heap[1]==null) {
			Email temp = this.heap[0];
			this.heap[0]=null;
			this.size--;
			return temp;
		}else {
			//swaps front and last elements
			Email temp = this.heap[0];
			this.heap[0]=this.heap[this.size-1];
			this.heap[this.size-1]=null;
			this.size--;
			this.heapifyDown(0);
			return temp;
		}
	}
	/**
	 * String of all IDs of emails
	 * @return
	 */
	public String[] getIDs() {
		String [] ids = new String [this.size];
		for(int i = 0; i<this.size;i++) {
			ids[i]=this.heap[i].getID();
		}
		return ids;
	}
	/**
	 * Rank emails
	 * @return
	 */
	public String[] rankEmails() {
		Email [] e = new Email[this.size];//creates new email array 
		String []email = new String[this.size];

		for(int j =0; j<this.size;j++) {//copy heap into new array
			e[j]=this.heap[j];
		}
		e = this.heapSort(e,1);//calls heapsort

		for(int i = 0; i<this.size;i++) {
			email[i]=e[i].getID()+ " -- " + e[i].getSpamScore() + "\n";//returns string
		}

		return email;


	}
	/**
	 * Sorts heap
	 * @param emails email array
	 * @param index 
	 * @return
	 */
	private Email [] heapSort(Email [] emails,int index) {
		if (index==this.size) {
			return emails;//sort is complete
		}else {
			int maxIndex=index;
			int max = emails[index].getSpamScore();
			for (int i = index+1; i<this.size;i++) {//iterates for max index
				if(emails[i].getSpamScore()>max) {
					maxIndex = i;
					max=emails[i].getSpamScore();
				}
			}
			emails = (Email[]) this.swap(index, maxIndex, emails);//swaps index
			index++;
			return this.heapSort(emails,index );
		}


	}
	/**
	 * Content of email in queue
	 * @param id
	 * @return String array of content
	 */
	public String[] getWords(String id) {
		int i = 0;
		while(this.heap[i].getID().equals(id)==false && this.size>i) {
			i++;
		}
		if(this.heap[i]==null || this.heap[i].getID().equals(id)==false) {//if heap location is null or doesn't equal id
			throw new NoSuchElementException();
		}
		else {
			return this.heap[i].getWords();
		}
	}
	/**
	 * Size
	 * @return
	 */
	public int size() {
		return this.size;
	}
}
