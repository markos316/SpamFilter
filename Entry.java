package main;

public class Entry<V> {
	String key;
	V value;
	
	public Entry(String key, V value) {
		this.key=key;
		this.value=value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public V getValue() {
		return this.value;
	}
	
	public void setValue(V value) {
		this.value=value;
	}
}
