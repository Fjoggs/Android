package vegardfj.hangman;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class WordLogic {
	private ArrayList<Word> unusedWordList;
	private ArrayList<Word> usedWordList;
	private Locale currentLocale;
	private static final String TAG = "WordLogic";
	private int wordLength;
	
	public WordLogic(Locale currentLocale) {
		this.currentLocale = currentLocale;
		unusedWordList = new ArrayList<Word>();
		usedWordList = new ArrayList<Word>();
		initiateWordList();
	}
	
	public Word getRandomWord() {
		Random random = new Random();
		return unusedWordList.get(random.nextInt(unusedWordList.size()));
	}
	
	
	public void markWordAsUsed(int id) {
		usedWordList.add(unusedWordList.get(id));
		unusedWordList.remove(id);
		System.out.println(TAG+": Removed "+unusedWordList.get(id).getWord());
	}
	
	public boolean checkForLetter(Word w, String letter) {
		String word = w.getWord();
		for(int i=0;i<word.length();i++) {
			if(Character.toString(word.charAt(i)).equalsIgnoreCase(letter)) {
				System.out.println(TAG+": The letter '"+letter+"' was correct.");
				checkIfLastLetter(w);
				return true;
			}
		}
		System.out.println(TAG+": The letter '"+letter+"' was wrong.");
		return false;
	}
	
	public ArrayList<Word> getUnusedWordList() {
		return unusedWordList;
	}

	public void setUnusedWordList(ArrayList<Word> unusedWordList) {
		this.unusedWordList = unusedWordList;
	}

	public ArrayList<Word> getUsedWordList() {
		return usedWordList;
	}

	public void setUsedWordList(ArrayList<Word> usedWordList) {
		this.usedWordList = usedWordList;
	}
	
	private boolean checkIfLastLetter(Word w) {
		wordLength = w.getWordLength();
		if(wordLength==0) {
			return true;
		} else {
			wordLength--;
			w.setWordLength(wordLength);
			System.out.println(TAG+": Amount of letters left is "+wordLength+".");
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
