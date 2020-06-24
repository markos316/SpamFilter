package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Email;
import main.PriorityQueue;

class StudentQueueTest {

	@Test
	void testInsert() {
		PriorityQueue Q = new PriorityQueue();
		
		Email e = new Email("1","hello");
		e.setSpamScore(9);
		
		Email e1 = new Email("1","hello");
		e1.setSpamScore(11);
		
		Email e2 = new Email("1","hello");
		e2.setSpamScore(13);
		Email e3 = new Email("1","hello");
		e3.setSpamScore(2);
		
		Q.insert(e);
		Q.insert(e1);
		Q.insert(e2);
		Q.insert(e3);
		
		assertEquals(13,Q.getMaximumSpamScore());
		assertEquals(9,Q.heap[1].getSpamScore());
		assertEquals(4,Q.size());
	}
	
	
	@Test
	void testExtract() {
		PriorityQueue Q = new PriorityQueue();
		
		Email e = new Email("1","hello");
		e.setSpamScore(9);
		
		Email e1 = new Email("1","hello");
		e1.setSpamScore(11);
		
		Email e2 = new Email("1","hello");
		e2.setSpamScore(13);
		Email e3 = new Email("1","hello");
		e3.setSpamScore(2);
		
		Email e4 = new Email("1","hello");
		e4.setSpamScore(5);
		
		Email e5 = new Email("1","hello");
		e5.setSpamScore(12);
		

		Q.insert(e);
		Q.insert(e1);
		Q.insert(e2);
		Q.insert(e3);
		Q.insert(e4);
		Q.insert(e5);
		
		assertEquals(13,Q.extractMaximum().getSpamScore());
		assertEquals(12,Q.extractMaximum().getSpamScore());

	}
	
	@Test
	void testUpdateScore() {
		PriorityQueue Q = new PriorityQueue();
		
		Email e = new Email("1","hello");
		e.setSpamScore(9);
		
		Email e1 = new Email("2","hello");
		e1.setSpamScore(11);
		
		Email e2 = new Email("3","hello");
		e2.setSpamScore(13);
		
		Email e3 = new Email("4","hello");
		e3.setSpamScore(2);
		
		Email e4 = new Email("5","hello");
		e4.setSpamScore(5);
		
		Email e5 = new Email("6","hello");
		e5.setSpamScore(12);
		

		Q.insert(e);
		Q.insert(e1);
		Q.insert(e2);
		Q.insert(e3);
		Q.insert(e4);
		Q.insert(e5);
		
		Q.updateSpamScore("3", 5);
		assertEquals(12, Q.getMaximumSpamScore());
		Q.updateSpamScore("6", 8);
		assertEquals(11, Q.getMaximumSpamScore());
		
		Q.updateSpamScore("1", 15);
		
		String[] r = Q.rankEmails();
		assertEquals("15",r[0].subSequence(r[0].length()-3, r[0].length()-1));

	}

}
