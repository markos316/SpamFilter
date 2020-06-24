package main;

public class Email {
	
	public String id;
	public String [] content;
	public int spamScore;

	public Email(String id, String contents) {
		this.id=id;
		this.content=contents.split(" ");
	}
	
	public String getID() {
		return this.id;
	}
	
	public String[] getWords() {
		return this.content;
	}
	
	public int getSpamScore() {
		return this.spamScore;
	}
	
	public void setSpamScore(int score) {
		this.spamScore=score;
	}
}
