package vegardfj.hangman;

import java.util.ArrayList;
import java.util.Locale;

public class WordLogic {
	private Locale currentLocale;
	private static final String TAG = "WordLogic";
	private int wordLength;
	private boolean lastLetterFound;
	private ArrayList<Word> unusedWordList;
	
	public WordLogic(Locale currentLocale) {
		this.currentLocale = currentLocale;
		lastLetterFound = false;
		unusedWordList = new ArrayList<Word>();
		initiateWordList();
	}

	public boolean checkForLetter(Word w, String letter, ArrayList<String> usedCharList) {
		boolean letterFound = false;
		String word = w.getWord();
		for(int i=0;i<word.length();i++) {
			if(Character.toString(word.charAt(i)).equalsIgnoreCase(letter)) {
				if(!(usedCharList.contains(letter))) {
					System.out.println(TAG+": The letter '"+letter+"' was correct.");
					w.unmaskLetter(i, letter);
					letterFound = true;
					lastLetterFound = checkIfLastLetter(w);
				}
			}
		}
		return letterFound;
	}

	public boolean isLastLetterFound() {
		return lastLetterFound;
	}

	public void setLastLetterFound(boolean lastLetterFound) {
		this.lastLetterFound = lastLetterFound;
	}
	
	public ArrayList<Word> getUnusedWordList() {
		return unusedWordList;
	}

	public void setUnusedWordList(ArrayList<Word> unusedWordList) {
		this.unusedWordList = unusedWordList;
	}

	private boolean checkIfLastLetter(Word w) {
		wordLength = w.getWordLength();
		if(wordLength!=0) {
			wordLength--;
			w.setWordLength(wordLength);
			System.out.println(TAG+": Amount of letters left is "+wordLength+".");
		}
		if(wordLength==0) {
			lastLetterFound = true;
			System.out.println(TAG+": Congratulations, you guessed the right word!");
			w.remaskLetter();
			w.setWordLength(w.getWord().length());
			return true;
		}
		return false;
	}
	
	private void initiateWordList() {
		if (currentLocale.getLanguage().equals("en")) {
			initiateEnglishWordList();
		} else if (currentLocale.getLanguage().equals("no")) {
			initiateNorwegianWordList();
		}
	}
	
	private void initiateEnglishWordList() {
		unusedWordList.add(new Word("Love", "Emotion"));
		unusedWordList.add(new Word("Hate", "Emotion"));
		unusedWordList.add(new Word("Football", "Sport"));
		unusedWordList.add(new Word("Basketball", "Sport"));
		unusedWordList.add(new Word("Reading", "Verb"));
		unusedWordList.add(new Word("Jumping", "Verb"));
		unusedWordList.add(new Word("English", "Language"));
		unusedWordList.add(new Word("Norwegian", "Language"));
		unusedWordList.add(new Word("Trance", "Music Genre"));
		unusedWordList.add(new Word("Rock and Roll", "Music Genre"));
	}
	
	private void initiateNorwegianWordList() {
		unusedWordList.add(new Word("Kjærlighet", "Følelser"));
		unusedWordList.add(new Word("Hat", "Følelser"));
		unusedWordList.add(new Word("Fotball", "Sport"));
		unusedWordList.add(new Word("Basketball", "Sport"));
		unusedWordList.add(new Word("Lesing", "Verb"));
		unusedWordList.add(new Word("Hopping", "Verb"));
		unusedWordList.add(new Word("Engelsk", "Språk"));
		unusedWordList.add(new Word("Norsk", "Språk"));
		unusedWordList.add(new Word("Trance", "Musikksjanger"));
		unusedWordList.add(new Word("Rock and Roll", "Musikksjanger"));
	}
}
