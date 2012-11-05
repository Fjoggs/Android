package no.hist.vegardfj.oving6;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {
	HttpHandler httpHandler = new HttpHandler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_login_fragment, container, false);
		return view;
	}
	
	public void tryLogIn(String playerName, String cardNumber) throws ClientProtocolException, IOException {
		httpHandler.setCardNumber(cardNumber);
		httpHandler.setPlayerName(playerName);
		String responseBody = httpHandler.httpGet();
		Log.d("tryLogIn", responseBody);
	}
}
