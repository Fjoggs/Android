package no.hist.vegardfj.oving6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpHandler {
	private String playerName;
	private String cardNumber;

	public String httpGet() throws ClientProtocolException, IOException {
		String url = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp" +
		URLEncodedUtils.format(parameterHandler(), null);		
		HttpGet request = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpClient client = new DefaultHttpClient();
		String responseBody = client.execute(request, responseHandler);
		return responseBody;
	}

	public String httpPost() throws ClientProtocolException, IOException {
		String url = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp";
		HttpPost request = new HttpPost(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		UrlEncodedFormEntity formParameters = new UrlEncodedFormEntity(parameterHandler());
		request.setEntity(formParameters);
		HttpClient client = new DefaultHttpClient();
		String responseBody = client.execute(request,responseHandler);
		return responseBody;
	}
	public List<BasicNameValuePair> parameterHandler() {
		List<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>(); 
		parameterList.add(new BasicNameValuePair("Navn", getPlayerName())); 
		parameterList.add(new BasicNameValuePair("etternavn", getCardNumber()));
		return parameterList;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
}
