package vegardfj.hangman;

public class Word {
	private String word;
	private String tag;
	private int wordLength;
	
	public Word(String word, String tag) {
		this.word = word;
		this.tag = tag;
		wordLength = word.length();
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
}
