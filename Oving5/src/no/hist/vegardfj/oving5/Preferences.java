package no.hist.vegardfj.oving5;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// load the XML preferences file
		addPreferencesFromResource(R.xml.preferences);
	}
}