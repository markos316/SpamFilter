/**
 * Spam filter class that updates emails spamscore and filters them
 * Known bugs: none
 * 
 * @author Markos Alemu
 * markos@brandeis.edu
 * May 9, 2019
 * COSI21A PA3
 */
package main;

public class SpamFilter {

	public int threshold;//threshold of spamscore
	private HashTable spamWords = new HashTable();//spamwords tracker
	private HashTable goodWords = new HashTable();//non spam tracker
	private HashTable marker = new HashTable();//marks if word has already been seen
	private PriorityQueue emails = new PriorityQueue();//stores emails
	private double spams = 0;//counts number of spam words
	private double safes = 0;//counts number of good words

	/**
	 * Constructor
	 * @param threshold
	 */
	public SpamFilter(int threshold) {
		this.threshold=threshold;
	}
	/**
	 * Sets threshold
	 * @param threshold
	 */
	public void setThreshold(int threshold) {
		this.threshold=threshold;
	}
	/**
	 * Gets spamicity of a word
	 * @param word
	 * @return
	 */
	public double getSpamicity(String word) {
		double wordinspam;
		double wordinsafe;
		if(this.spamWords.get(word)==null) {
			return 0;//if word doesn't appear in spam words, returns 0
		}else {
			wordinspam=(int)this.spamWords.get(word) /this.spams;//gets frequency
		}
		if(this.goodWords.get(word)==null) {
			wordinsafe=0;//sets to 0 if word doesn't appear in goodwords list
		}else {
			wordinsafe=(int)this.goodWords.get(word)/this.safes;
		}

		return wordinspam/(wordinspam+wordinsafe);

	}

	/**
	 * Calculates spam score of array of words/email
	 * @param words
	 * @return spamscore
	 */
	public int calculateSpamScore(String[] words) {
		int spamScore = 0;
		for(int i = 0; i<words.length; i++) {//spamscore formula
			double spamicity = this.getSpamicity(words[i].toLowerCase());
			if(spamicity>=0.9) {
				spamScore+=4;
			}else if(spamicity>=0.75) {
				spamScore+=2;
			}else if(spamicity>=0.5) {
				spamScore+=1;
			}
		}
		return spamScore;
	}
	/**
	 * Receive's emails 
	 * @param emails
	 */
	public void receive(Email[] emails) {
		for(int i = 0; i<emails.length; i++) {
			//sets spamscore of email and inserts into priority queue
			emails[i].setSpamScore(this.calculateSpamScore(emails[i].getWords()));
			this.emails.insert(emails[i]);
		}
	}
	/**
	 * filters emails with spamscore above threshold and spam emails that may now be below limit
	 * @return String
	 */
	public String filter() {
		String r = "";
		if(this.emails.size()!=0) {
			while(this.emails.size()!=0 && this.emails.getMaximumSpamScore()>this.threshold ) {
				r += this.emails.extractMaximum().getID() + "\n";

			}
		}
		return r;
	}
	/**
	 * updates spamscores of emails in priority priority
	 */
	public void updateSpamScores() {
		if(this.emails.size()!=0) {
			String [] r = this.emails.getIDs();

			for(int i = 0; i<r.length;i++) {
				String []r1 = this.emails.getWords(r[i]);
				this.emails.updateSpamScore(r[i], this.calculateSpamScore(r1));
			}
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Trains spamfilter with spam and good words
	 * @param emails
	 * @param isSpam
	 */
	public void train(Email[] emails, boolean[] isSpam) {
		for (int i = 0; i<emails.length;i++) {
			if(isSpam[i]==true) {
				this.spams++;
				String [] s = emails[i].getWords();
				for(int j = 0; j<s.length; j++) {
					String word = s[j].toLowerCase();
					if(this.marker.get(word)==null) {//if word has not already been marked
						if(this.spamWords.get(word)==null) {//if word isnot in filter, insert
							this.spamWords.put(word, 1);
						}else {//if word is already in filter, update it's count
							this.spamWords.put(word,1 + (int) this.spamWords.get(word));
						}
						this.marker.put(word, 0);
					}

				}
			}else {
				String [] st = emails[i].getWords();
				this.safes++;
				for(int j = 0; j<st.length; j++) {
					String word = st[j].toLowerCase();
					if(this.marker.get(word)==null) {
						if(this.goodWords.get(word)==null) {
							this.goodWords.put(word, 1);
						}else {
							this.goodWords.put(word, 1+(int) this.goodWords.get(word));
						}
						this.marker.put(word, 0);
					}

				}
			}
			this.updateSpamScores();
			HashTable h = new HashTable();
			this.marker=h;
		}

	}
	/**
	 * Ranks emails according to spam score
	 * @return
	 */
	public String getEmailRanking() {
		String [] f = this.emails.rankEmails();
		String r = "";
		for(int i = 0; i<f.length;i++) {
			r+=f[i];
		}

		return r;
	}
}
