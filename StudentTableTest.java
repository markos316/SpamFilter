package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.HashTable;

class StudentTableTest {


	
	@Test
	void testInsertGet() {
		HashTable h = new HashTable();
		h.put("hello", 1);
		h.put("hi", 3);
		h.put("hey", 9);
		h.put("hoy", 12);
		h.put("l", 5);
		h.put("fun", 10);
	
		assertEquals(h.get("hello"),1);
		assertEquals(h.get("hi"),3);
		assertEquals(h.get("hey"),9);
		assertEquals(h.get("hoy"),12);
		assertEquals(h.get("l"),5);
		assertEquals(h.get("fun"),10);
		
	}
	
	@Test
	void testDelete() {
		HashTable h = new HashTable();
		h.put("hello", 1);
		h.delete("hello");
		h.put("test", 2);
		h.put("hi", 3);
		h.put("hey", 9);
		h.put("hoy", 12);
		h.put("l", 5);
		h.put("fun", 10);
		h.put("LOL", 9);
		
		HashTable h1 = new HashTable();
		
		
		assertEquals(null,h.get("hello"));
		assertEquals(null,h1.delete("key"));
		
		
	}
	
	@Test
	void testResize() {
		HashTable h = new HashTable();
		h.put("hello", 1);
		h.put("hi", 3);
		h.put("hey", 8);
		h.put("hoy", 8);
		h.put("l", 8);
		h.put("fun", 10);
		h.put("love", 21);
		h.put("cosi21", 1);
		assertEquals(h.length,22);
	}

}
