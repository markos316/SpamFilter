package main;
/**
 * Hashtable abstract data type
 * Known bugs: none
 * 
 * @author Markos Alemu
 * markos@brandeis.edu
 * May 9, 2019
 * COSI21A PA3
 */
public class HashTable<V> {


	// KEEP THIS PUBLIC 
	//Entry data
	public Entry<V>[] entries;
	//amount of space available
	public int length;
	//number of entries
	public int entriesCount;
	//dummy variable for removed item
	public V dummy = (V) "holdervariable";


	// KEEP THIS PUBLIC AND NOT FINAL 
	// However, feel free to change the load factor 
	public static double LOAD_FACTOR = 0.5;

	@SuppressWarnings("unchecked")
	public HashTable() {

		// Use this to create generic arrays of type "Entry<V>" 
		// feel free to change the size of this array from 11 
		int length = 11;
		entries = (Entry<V>[]) new Entry[length];
		this.length=length;

	}
	/**
	 * Searches certain element
	 * @param key of element being searched 
	 * @return returns element
	 */
	public V get(String key) {
		int index = this.getIterateTo(key);//finds index of element

		if(this.entries[index]==null || this.entries[index].getValue().equals(this.dummy)) {//if index is null, means element doesn'e exist
			return null;
		}else {
			return this.entries[index].getValue();//returns value
		}
	}
		
	/**
	 * puting element into array
	 * @param key of element to be added
	 * @param value of element to be added
	 */
	public void put(String key, V value) {
		Entry e = new Entry(key,value);

		int location = this.iterateTo(key);//finds spot to put element in
		
		if(this.entries[location]==null || this.entries[location].getKey().equals(this.dummy)) {
			this.entriesCount++;//iterates count if new entry
		}
		this.entries[location]=e;//adds element
		this.checkResize();//checks if resize is necessary
	}


	/**
	 * checks if resize is necessary
	 */
	@SuppressWarnings("unchecked")
	private void checkResize() {
		/**
		 * if past them limit, create new array of entries
		 */
		if( ((double)this.entriesCount/this.length)>this.LOAD_FACTOR) {
			Entry<V>[] ent = new Entry[this.length*2];
			Entry<V>[] temp = new Entry[this.length];
			
			for(int j = 0; j<this.length;j++) {//transfer items
				if(this.entries[j]!=null) {
					
					temp[j]=this.entries[j];
				}
				
			}
			this.entries=ent;
			this.length=this.length*2;

			for(int i = 0; i<this.length/2;i++) {
				if(temp[i]!=null) {
					this.entries[this.iterateTo(temp[i].getKey())]=temp[i];
				}
			}
		}

	}
	/**
	 * Deletes element
	 * @param key
	 * @return deleted element
	 */
	public V delete(String key) {
		int v = this.getIterateTo(key);
		//checks if entries location is null meaning it doesnt exist
		if(this.entries[v]==null) {
			return null;
		}else {
			//otherwise deletes and returns element
			V value = this.entries[v].getValue();
			this.entries[v].setValue(this.dummy);
			this.entriesCount--;
			return value;
		}

		
	}
	/**
	 * Iterate until empty spot is found to add
	 * @param key
	 * @return
	 */
	private int iterateTo(String key) {
		int i = 1;
		int index = this.hashFunction(key, i);
		//checks if index is a spot where an element can be added
		while(this.entries[index]!=null && this.entries[index].getValue().equals(this.dummy)==false && this.entries[index].getKey().equals(key)==false) {
			i++;
			index=this.hashFunction(key,i );
		
		}
		return index;
	}
	/**
	 * Checks if element is in index
	 * @param key
	 * @return
	 */
	private int getIterateTo(String key) {
		int i = 1;
		int index = this.hashFunction(key, i);
		//iterates until spot is either null or element being searched
		while(this.entries[index]!=null && this.entries[index].getKey().equals(key)==false) {
			i++;
			index=this.hashFunction(key,i);
			
		}
		return index;
	}
	/**
	 * Hash function
	 * @param key
	 * @param i to increment for probing
	 * @return index
	 */
	private int hashFunction(String key,int i) {

		return ((key.length()%5 + i*key.charAt(0)+i) % this.length);
	}
	/**
	 * Returns all keys in table
	 * @return
	 */
	public String[] getKeys() {
		String [] keys = new String[this.entriesCount];
		int i = 0;
		int location = 0;
		while (location!=this.entriesCount) {
			if(this.entries[i]!=null) {
				keys[location]=this.entries[i].key;
				location++;
				i++;
			}else {
				i++;
			}
		}
		return keys;
	}
	/**
	 * Size of table
	 * @return
	 */
	public int size() {
		return this.entriesCount;
	}
}
