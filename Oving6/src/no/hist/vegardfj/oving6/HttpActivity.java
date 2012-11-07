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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HttpActivity extends Activity {
	private HttpWrapperThreaded network;
	final static String TAG = "HttpActivity";
	final static String urlToServer = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp"; 
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG,"Contacting server...");
        network = new HttpWrapperThreaded(this, urlToServer);
    }
	
	//Method for showing response from HTTP server
	public void showResponse(String response){
		Log.i(TAG,"Server responded with: " + response);
		Toast.makeText(getBaseContext(), "Look in log for response!!!", Toast.LENGTH_LONG).show();
	}
    
    /* Lets make some parameters for the HTTP request */
    private List<BasicNameValuePair> createRequestValues(){
    	List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
        valueList.add(new BasicNameValuePair("fornavn","Øystein Marius"));
        valueList.add(new BasicNameValuePair("etternavn","Knaus"));   
        return valueList;
    }
    
    public boolean onCreateOptionsMenu(Menu meny){
    	super.onCreateOptionsMenu(meny);//kaller metoden som vi arver, er dog ikke nødvendig
    	return false;
    }

    
    public boolean onOptionsItemSelected(MenuItem item){
    	if (item.getTitle().equals("Send HTTP_GET"))
    		network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, createRequestValues());
    	else if (item.getTitle().equals("Send HTTP_POST"))
            network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_POST, createRequestValues());
    	else 
    		network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET_WITH_HEADER_IN_RESPOMSE, createRequestValues());
    	
    	return true; //hvorfor true her? Se API-dokumentasjonen!!
    }
    
    public void onClickLogIn(View v) throws ClientProtocolException, IOException {
    	EditText nameEditText1 = (EditText) findViewById(R.id.name_editText);
    	EditText nameEditText2 = (EditText) findViewById(R.id.number_editText);
    	List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
        valueList.add(new BasicNameValuePair("navn", nameEditText1.getText().toString()));
        valueList.add(new BasicNameValuePair("kortnummer", nameEditText2.getText().toString()));
        Log.d("ValueList", nameEditText1.getText().toString()+" "+nameEditText2.getText().toString());
    	network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, valueList);
    	Intent myIntent = new Intent(HttpActivity.this, GameActivity.class);
    	HttpActivity.this.startActivity(myIntent);
    }

}