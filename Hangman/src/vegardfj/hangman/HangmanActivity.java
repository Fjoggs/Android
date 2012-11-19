package vegardfj.hangman;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HangmanActivity extends Activity {
	EditText enterLetter;
	ImageView progressImage;
	ListView wrongCharacters;
	TextView wordToGuess;
	TextView currentScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hangman);
		enterLetter = (EditText) findViewById(R.id.activity_hangman_edittext_enter_letter);
		progressImage = (ImageView) findViewById(R.id.activity_hangman_imageview_progress);
		wrongCharacters = (ListView) findViewById(R.id.activity_hangman_listview_wrong_characters);
		wordToGuess = (TextView) findViewById(R.id.activity_hangman_textview_word);
		currentScore = (TextView) findViewById(R.id.activity_hangman_textview_score_current);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hangman, menu);
		return true;
	}
	
	public void onClickTryLetter(View v) {
		
	}

}
