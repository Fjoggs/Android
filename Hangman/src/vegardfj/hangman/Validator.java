package vegardfj.hangman;

public class Validator {
	private String[] validCharacters;
	private static final String TAG = "Validator";

	public Validator() {
		initiateArray();
	}

	private void initiateArray() {
		validCharacters = new String[] { "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z", "æ", "ø", "å" };
	}
	
	public boolean validateLetter(String letter) {
		for(int i=0;i<validCharacters.length;i++) {
			if(letter.equalsIgnoreCase(validCharacters[i])) {
				System.out.println(TAG+": The letter '"+letter+"' is a valid letter.");
				return true;
			}
		}
		System.out.println(TAG+": The letter '"+letter+"' is not a valid letter.");
		return false;
	}

	public String[] getValidCharacters() {
		return validCharacters;
	}

	public void setValidCharacters(String[] validCharacters) {
		this.validCharacters = validCharacters;
	}
}
