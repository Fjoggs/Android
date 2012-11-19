package vegardfj.hangman;

import java.util.Locale;


public class GameLogic {
	private WordLogic wordLogic;
	private int wordsCompleted;
	private int wordsFailed;
	private Word currentWord;
	
	public GameLogic(Locale currentLocale) {
		wordLogic = new WordLogic(currentLocale);
		currentWord = wordLogic.getRandomWord();
	}

	public int getWordsCompleted() {
		return wordsCompleted;
	}

	public void setWordsCompleted(int wordsCompleted) {
		this.wordsCompleted = wordsCompleted;
	}

	public int getWordsFailed() {
		return wordsFailed;
	}

	public void setWordsFailed(int wordsFailed) {
		this.wordsFailed = wordsFailed;
	}
}
