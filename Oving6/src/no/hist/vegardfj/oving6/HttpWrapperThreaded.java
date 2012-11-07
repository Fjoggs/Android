/* Tomas Holt 20.09.2012 */ 
/* Class for making HTTP request in its own thread. Server response are "posted" to HttpActivity.showResponse(String)
*/

package no.hist.vegardfj.oving6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class HttpWrapperThreaded {
	private HttpClient client = new DefaultHttpClient();//reuse this object to ensure that sessions are working!
	private HttpActivity activity;
	private String urlToServer;
	private HttpRequestType typeOfRequest;
	private final String TAG = "HTTPWRAPPER";
	
	public static enum HttpRequestType { //Used to decide which HTTP request method to use
	    HTTP_GET, HTTP_POST, HTTP_GET_WITH_HEADER_IN_RESPOMSE
	}
	
	public HttpWrapperThreaded(HttpActivity activity, String urlToServer){
		this.activity = activity;
		this.urlToServer = urlToServer;
	}
	
	public void runHttpRequestInThread(HttpRequestType typeOfRequest, List<BasicNameValuePair> requestValues){
		this.typeOfRequest = typeOfRequest;
		new RequestThread().execute(requestValues);//start new thread for each request - can't restart finished thread
	}
	
	/*Thread class for executing one of the HTTP request methods (below) */
	private class RequestThread extends AsyncTask<List<BasicNameValuePair>,String,String> {
		
		/* Thread method */
		protected String doInBackground(List<BasicNameValuePair>... v){
			try{
				if (typeOfRequest == HttpRequestType.HTTP_GET) 			return httpGet(v[0]);
				else if (typeOfRequest == HttpRequestType.HTTP_POST) 	return httpPost(v[0]);
				else return httpGetWithHeader(v[0]);
			}catch(Exception e){
				Log.e(TAG,e.getMessage());
				return e.getMessage();//return errorMessage
			}
		}
			
		protected void onPostExecute(String response){
			activity.showResponse(response);
		}
	}/*END THREAD CLASS */
	
	/* Sends HTTP GET request and returns body of response from server as String 
	 * This method should be done in its own thread.
	 * */ 
	public String httpGet(List<BasicNameValuePair> parameterList) throws IOException {
		String url = urlToServer + "?" + URLEncodedUtils.format(parameterList,null);
		String responseStr = "";
		HttpGet request = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		responseStr = client.execute(request,responseHandler);
		return responseStr;
	}
	
	/* Sends HTTP POST request and returns body of response from server as String 
	 * This method should be done in its own thread.
	 * */
	public String httpPost(List<BasicNameValuePair> parameterList) throws IOException {		
		HttpPost request = new HttpPost(urlToServer);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		UrlEncodedFormEntity formParameters = new UrlEncodedFormEntity(parameterList);
		request.setEntity(formParameters);
		String responseBody = client.execute(request,responseHandler);
		return responseBody;
	}
	
	/* Sends HTTP GET request and returns body of response from server as HTTPResponse object. 
	 * The response object contains HTTP headers and body. 
	 * Also notice that in this case the body is read line by line from a stream (in.readLine()).
	 * In most cases one of the above methods should suffice.
	 * */ 
	public String httpGetWithHeader(List<BasicNameValuePair> parameterList) throws IOException{
		String url = urlToServer + "?" + URLEncodedUtils.format(parameterList,null);
		String responseStr = "";
		BufferedReader in = null;
		try{
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			
	        /* Get the HTTP-header and add all names/values to response **/
			Header[] headers = response.getAllHeaders();
			responseStr += "(Header name : Header value \n)";
	        for (int i=0; i < headers.length; i++) {
	            Header h = headers[i];
	            responseStr += h.getName() + " : " + h.getValue() + "\n";//add header values to response
	        }
	        
	        /* Read body of HTTP-message from server and add to response */
	        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        StringBuffer body = new StringBuffer("");
	        String line = null;
	        while ((line = in.readLine()) != null){
	        	body.append(line + "\n");
	        }
	        
	        return responseStr + body;	        
		}finally{
			try{
				in.close();//sørg for å alltid lukke strøm
			}catch(IOException ie){
				Log.e(TAG,ie.toString());
			}
		}
	}
}
