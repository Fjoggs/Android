/* Tomas Holt, 20.09.20012.
 * This class is for testing HttpWrapper. For output look in the log. */

package no.hist.vegardfj.oving6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HttpActivity extends Activity {
	private HttpWrapperThreaded network;
	final static String TAG = "HttpActivity";
	final static String urlToServer = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.i(TAG, "Contacting server...");
		network = new HttpWrapperThreaded(this, urlToServer);
	}

	// Method for showing response from HTTP server
	public void showResponse(String response) {
		Log.i(TAG, "Server responded with: " + response);
		Toast.makeText(getBaseContext(), "Look in log for response!!!",
				Toast.LENGTH_LONG).show();
		setContentView(R.layout.activity_game);
		TextView textView_info = (TextView) findViewById(R.id.info_textView);
		textView_info.setText(response);
	}

	public boolean onCreateOptionsMenu(Menu meny) {
		super.onCreateOptionsMenu(meny);
		return false;
	}

	public void onClickLogIn(View v) throws ClientProtocolException,
			IOException {
		EditText nameEditText1 = (EditText) findViewById(R.id.name_editText);
		EditText nameEditText2 = (EditText) findViewById(R.id.number_editText);
		List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
		valueList.add(new BasicNameValuePair("navn", nameEditText1.getText()
				.toString()));
		valueList.add(new BasicNameValuePair("kortnummer", nameEditText2
				.getText().toString()));
		Log.d("ValueList", nameEditText1.getText().toString() + " "
				+ nameEditText2.getText().toString());
		network.runHttpRequestInThread(
				HttpWrapperThreaded.HttpRequestType.HTTP_GET, valueList);
	}

	public void onClickSendAnswer(View v) throws ClientProtocolException,
			IOException {
		EditText editText_answer = (EditText) findViewById(R.id.answer_editText);
		List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
		Log.d(TAG, editText_answer.getText().toString());
		valueList.add(new BasicNameValuePair("tall", editText_answer.getText()
				.toString()));
		network.runHttpRequestInThread(
				HttpWrapperThreaded.HttpRequestType.HTTP_GET, valueList);
	}
	
	public void onClickRestartApplication(View v) {
		setContentView(R.layout.activity_login);
	}

}