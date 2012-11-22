package vegardfj.hangman;

public class Word {
	private String word;
	private String tag;
	private String maskedWord;
	private int wordLength;
	private static final String TAG = "Word";
	
	public Word(String word, String tag) {
		this.word = word;
		this.tag = tag;
		wordLength = word.length();
		createMaskedWord();
	}
	
	public void unmaskLetter(int index, String l) {
		StringBuilder sb = new StringBuilder(maskedWord);
		char letter = l.charAt(0);
		sb.setCharAt(index, letter);
		maskedWord = sb.toString();
		System.out.println(TAG+": New masked word is "+maskedWord);
	}
	
	public void remaskLetter() {
		createMaskedWord();
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getWordLength() {
		return wordLength;
	}

	public void setWordLength(int wordLength) {
		this.wordLength = wordLength;
	}
	
	public String getMaskedWord() {
		return maskedWord;
	}

	public void setMaskedWord(String maskedWord) {
		this.maskedWord = maskedWord;
	}
	
	private void createMaskedWord() {
		maskedWord = "";
		for(int i=0;i<word.length();i++) {
			if(word.charAt(i)==' ') {
				maskedWord += " ";
				wordLength--;
			} else {
				maskedWord += "*";
			}
		}
		System.out.println(TAG+": "+maskedWord);
	}
	
}
