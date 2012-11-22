package vegardfj.hangman;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.util.Log;

public class GameLogic {
	private final static String TAG = "GameLogic";
	private Validator validator;
	private WordLogic wordLogic;
	private int wordsCompleted;
	private int wordsFailed;
	private int tries;
	private Word currentWord;
	private ArrayList<Word> usedWordList;
	private ArrayList<Word> unusedWordList;
	private ArrayList<String> usedCharList;
	private ArrayList<String> wrongCharList;

	public GameLogic(Locale currentLocale) {
		wordLogic = new WordLogic(currentLocale);
		validator = new Validator();
		initiateArrayLists();
		tries = 0;
		wordsCompleted = 0;
		wordsFailed = 0;
		getRandomWord();
	}

	public void markWordAsUsed() {
		usedWordList.add(currentWord);
		for(int i=0;i<unusedWordList.size();i++){
			if(unusedWordList.get(i).equals(currentWord)){
				System.out.println(TAG + ": Removed "+ unusedWordList.get(i).getWord());
				unusedWordList.remove(i);
			}
		}
	}

	/*
	 * int letterFound 0 = false 1 = true 2 = used before This goes for every
	 * method returning ints like this.
	 */

	public int checkIfValidLetter(Word w, String letter) {
		boolean isValidLetter = validator.validateLetter(letter);
		int validLetter = 0;
		if (isValidLetter) {
			int status = checkForLetter(w, letter);
			if (status == 1) {
				validLetter = 1;
			} else if (status == 2) {
				validLetter = 2;
			} else if(status == 3) {
				validLetter = 3;
			}
		}
		return validLetter;
	}

	/*
	 * int letterFound 
	 * 0 = false 
	 * 1 = true 
	 * 2 = used before This goes for every
	 * 3 = letter was wrong
	 * method returning ints like this.
	 */
	private int checkForLetter(Word w, String letter) {
		int letterFound = 0;
		boolean isLetterFound = wordLogic.checkForLetter(w, letter,
				usedCharList);
		boolean status = isLetterUsedBefore(letter);
		if (isLetterFound && !status) {
			System.out.println(TAG + ": The letter '" + letter+ "' has not been used before.");
			usedCharList.add(letter);
			letterFound = 1;
		} else if (status) {
			System.out.println(TAG + ": The letter '" + letter+ "' has already been used.");
			letterFound = 2;
		} else {
			wrongCharList.add(letter);
			System.out.println(TAG + ": The letter '" + letter + "' was wrong.");
			if(isGameOver()) {
				System.out.println(TAG + ": You've died!");
			}
			tries++;
			System.out.println(TAG + ": You have " + (10-tries) + " tries left.");
			letterFound = 3;
		}
		return letterFound;
	}

	public void getRandomWord() {
		Random random = new Random();
		if(unusedWordList.size()>0) {
			Word word = unusedWordList.get(random.nextInt(unusedWordList.size()));
			currentWord = word;
			Log.d(TAG, "Word to guess is now: "+word.getWord().toString());
		} else {
			System.out.println(TAG+": You've cleared the game!");
		}
	}

	public WordLogic getWordLogic() {
		return wordLogic;
	}

	public void setWordLogic(WordLogic wordLogic) {
		this.wordLogic = wordLogic;
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

	public Word getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(Word currentWord) {
		this.currentWord = currentWord;
	}

	public ArrayList<Word> getUsedWordList() {
		return usedWordList;
	}

	public void setUsedWordList(ArrayList<Word> usedWordList) {
		this.usedWordList = usedWordList;
	}

	public ArrayList<String> getWrongCharList() {
		return wrongCharList;
	}

	public void setWrongCharList(ArrayList<String> wrongCharList) {
		this.wrongCharList = wrongCharList;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public ArrayList<Word> getUnusedWordList() {
		return unusedWordList;
	}

	public void setUnusedWordList(ArrayList<Word> unusedWordList) {
		this.unusedWordList = unusedWordList;
	}

	public ArrayList<String> getUsedCharList() {
		return usedCharList;
	}

	public void setUsedCharList(ArrayList<String> usedCharList) {
		this.usedCharList = usedCharList;
	}

	public boolean isLastLetterFound() {
		if (wordLogic.isLastLetterFound()) {
			tries = 0;
			wordLogic.setLastLetterFound(false);
			return true;
		}
		return false;
	}
	
	private boolean isGameOver() {
		if(tries>10) {
			return true;
		}
		return false;
	}

	private void initiateArrayLists() {
		unusedWordList = wordLogic.getUnusedWordList();
		usedWordList = new ArrayList<Word>();
		usedCharList = new ArrayList<String>();
		wrongCharList = new ArrayList<String>();
	}

	private boolean isLetterUsedBefore(String letter) {
		boolean letterIsUsed = false;
		System.out.println(TAG + ": Checking if the letter '" + letter
				+ "' is used before.");
		for (int i = 0; i < wrongCharList.size(); i++) {
			if (letter.equalsIgnoreCase(wrongCharList.get(i))) {
				letterIsUsed = true;
			}
		}
		for (int i = 0; i < usedCharList.size(); i++) {
			if (letter.equalsIgnoreCase(usedCharList.get(i))) {
				letterIsUsed = true;
			}
		}
		return letterIsUsed;
	}
}
