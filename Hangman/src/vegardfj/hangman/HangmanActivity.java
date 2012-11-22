package vegardfj.hangman;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HangmanActivity extends Activity {
	private static final String TAG = "HangmanActivity";
	private Locale locale;
	private String wrongCharString;
	private int pictureNumber;
	EditText enterLetter;
	ImageView progressImage;
	TextView wrongCharacters;
	TextView wordToGuess;
	TextView currentScore;
	GameLogic gameLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLocale();
		setContentView(R.layout.activity_hangman);
		wrongCharString = "";
		pictureNumber = 1;
		initiateLogic();
		initiateViews();
		setRandomWord();
		modifyEditText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hangman, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_quit:
			/*
			 * Release all resources please.
			 */
			finish();
			break;
		case R.id.menu_help:
			Intent intent = new Intent(HangmanActivity.this, HelpActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_english:
			// 0 = English Locale
			restartActivity(0);
			break;
		case R.id.menu_norwegian:
			// 1 = Norwegian Locale
			restartActivity(1);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickTryLetter(View v) {
		String letter = enterLetter.getText().toString();
		int status = gameLogic.checkIfValidLetter(gameLogic.getCurrentWord(), letter);
		if(status == 1) {
			refreshWordTextView();
			if(gameLogic.isLastLetterFound()) {
				progressImage.setImageResource(R.drawable.hangman_success);
				gameLogic.markWordAsUsed();
				setRandomWord();
				int correctWords = gameLogic.getWordsCompleted();
				gameLogic.setWordsCompleted(correctWords+1);
				refreshScoreBoard();
			}
		} else if(status == 2) {
			System.out.println(TAG+": Letter has been used before.");
		} else if(status == 3) {
			updateProgressPicture();
			refreshWrongCharTextView();
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(enterLetter.getWindowToken(), 0);
	}
	
	public void onClickNewWord(View v) {
		int wordsFailed = gameLogic.getWordsFailed();
		gameLogic.setWordsFailed(wordsFailed+1);
		setRandomWord();
	}
	
	private void setLocale() {
		System.out.println(TAG+": Setting locale to match systems default locale");
		locale = getResources().getConfiguration().locale;
		if(getIntent().getExtras()!=null) {
			Bundle bundle = getIntent().getExtras();
			int localeNumber = bundle.getInt("Locale");
			if(localeNumber==0) {
				//Set Locale to English.
				System.out.println(TAG+": Setting locale to English (EN).");
				locale = new Locale("en", "US");
				Locale.setDefault(locale);
				Configuration config = new Configuration();
				config.locale = locale;
				getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
			} else {
				//Set Locale to Norwegian.
				System.out.println(TAG+": Setting locale to Norwegian (NO)");
				locale = new Locale("no", "NO");
				Locale.setDefault(locale);
				Configuration config = new Configuration();
				config.locale = locale;
				getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
			}
		}
	}
	
	private void setRandomWord() {
		if(gameLogic.getUnusedWordList().isEmpty()) {
			Log.d(TAG, "Setting text for no words.");
			wordToGuess.setText(this.getString(R.string.no_words));
			progressImage.setImageResource(R.drawable.hangman_super_success);
		} else {
			Word word = gameLogic.getCurrentWord();
			wordToGuess.setText(word.getMaskedWord());
			gameLogic.setWrongCharList(new ArrayList<String>());
			gameLogic.setUsedCharList(new ArrayList<String>());
			resetGameWindow();
		}
	}
	
	private void refreshWordTextView() {
		Word word = gameLogic.getCurrentWord();
		if(word!=null) {
			wordToGuess.setText(word.getMaskedWord());
		}
	}
	
	private void resetGameWindow() {
		wrongCharacters.setText("");
		progressImage.setImageResource(R.drawable.ic_launcher);
		refreshScoreBoard();
	}
	
	private void refreshWrongCharTextView() {
		System.out.println(TAG+": Refreshing wrong character textview.");
		int position = gameLogic.getWrongCharList().size()-1;
		if(position>=0) {
			wrongCharString += gameLogic.getWrongCharList().get(position)+" ";
			wrongCharacters.setText(wrongCharString);
		}
	}
	
	private void restartActivity(int value) {
		Intent intent = getIntent();
		/*
		 * 0 = English Locale
		 * 1 = Norwegian Locale
		 */
		intent.putExtra("Locale", value);
		finish();
		startActivity(intent);
	}
	
	private void refreshScoreBoard() {
		String wordsCompleted = this.getString(R.string.textview_words_completed);
		String wordsFailed = this.getString(R.string.textview_words_failed);
		currentScore.setText(wordsCompleted+" "+gameLogic.getWordsCompleted()+"\n"+wordsFailed+" "+gameLogic.getWordsFailed());
	}
	
	private void initiateViews() {
		enterLetter = (EditText) findViewById(R.id.activity_hangman_edittext_enter_letter);
		progressImage = (ImageView) findViewById(R.id.activity_hangman_imageview_progress);
		wordToGuess = (TextView) findViewById(R.id.activity_hangman_textview_word);
		currentScore = (TextView) findViewById(R.id.activity_hangman_textview_score_current);
		wrongCharacters = (TextView) findViewById(R.id.activity_hangman_textview_wrong_characters);
	}
	
	private void initiateLogic() {
		gameLogic = new GameLogic(locale);
	}
	
	private void modifyEditText() {
		enterLetter.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (s != null && s.length() > 1) {
					s.delete(0, 1);
					Selection.setSelection(s, s.length());
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		});
	}
	
	private void updateProgressPicture() {
		if(pictureNumber==1) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_1);
		} else if(pictureNumber==2) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_2);
		} else if(pictureNumber==3) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_3);
		} else if(pictureNumber==4) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_4);
		} else if(pictureNumber==5) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_5);
		} else if(pictureNumber==6) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_6);
		} else if(pictureNumber==7) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_7);
		} else if(pictureNumber==8) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_8);
		} else if(pictureNumber==9) {
			pictureNumber++;
			progressImage.setImageResource(R.drawable.hangman_try_9);
		} else if(pictureNumber==10) {
			pictureNumber = 0;
			progressImage.setImageResource(R.drawable.hangman_try_10);
			setRandomWord();
			int wordsFailed = gameLogic.getWordsFailed();
			gameLogic.setWordsFailed(wordsFailed+1);
			refreshScoreBoard();
		}
	}
}
