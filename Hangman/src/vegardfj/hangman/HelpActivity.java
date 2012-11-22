package vegardfj.hangman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends Activity {
	TextView helpDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        helpDescription = (TextView) findViewById(R.id.activity_help_help);
    }
    
    public void onClickReturnToGame(View v) {
		finish();
    }
}
