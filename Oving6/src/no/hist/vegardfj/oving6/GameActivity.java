package no.hist.vegardfj.oving6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends Activity {
	private HttpWrapperThreaded network;
	final static String TAG = "HttpActivity";
	final static String urlToServer = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp";
	private int tries = 0;
	private TextView textView_info;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView_info = (TextView) findViewById(R.id.info_textView);
        textView_info.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ta_shansen, menu);
        return true;
    }
    
    public void onClickSendAnswer(View v) throws ClientProtocolException, IOException {
    	if(tries < 2) {
    		EditText editText_answer = (EditText) findViewById(R.id.answer_editText);
        	List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
            valueList.add(new BasicNameValuePair("kortnummer", editText_answer.getText().toString()));
        	network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, valueList);
        	tries++;
    	} else {
    		tries = 0;
        	Intent myIntent = new Intent(GameActivity.this, HttpActivity.class);
        	GameActivity.this.startActivity(myIntent);
    	}
    }
}
