package no.hist.vegardfj.oving6;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Fragment;

public class LoginFragment extends Fragment {

	public void loginAttempt(String loginName, int cardNumber) {
		String url = "http://tomcat.stud.aitel.hist.no/studtomas/ekko.jsp";
		HttpPost request = new HttpPost(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		UrlEncodedFormEntity formParameters;
		try {
			formParameters = new UrlEncodedFormEntity(stringHandler(loginName));
			request.setEntity(formParameters);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpClient client = new DefaultHttpClient();
		try {
			String responseBody = client.execute(request,responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	private List<BasicNameValuePair> stringHandler(String loginName) {
		List<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>(); 
		parameterList.add(new BasicNameValuePair("navn",loginName)); 
		return parameterList;
	}
}
