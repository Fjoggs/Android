package vegardfj.oving7.klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class Client extends Thread {
	private final static String TAG = "Client";
	private final static String IP = "10.0.2.2";
	private final static int PORT = 12345;
	int answer = 0;
	String stuff = "";
	int number1;
	int number2;

	public Client(int number1, int number2) {
		this.number1 = number1;
		this.number2 = number2;
	}
	
	public void run() {
		Socket s = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String data = "";
		try {
			Log.d(TAG, "Yeeeees, gooood, goood.");
			s = new Socket(IP, PORT);
			Log.v(TAG, "C: Connected to server" + s.toString());
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//String res = in.readLine();
			//Log.i(TAG, res);
			out.write(number1);
			out.write(number2);
			out.println("PING to server from client");
			Log.i(TAG, "Sendt to server: " +number1+ ", "+number2);
            StringBuilder sb = new StringBuilder();
            while ((data = in.readLine()) != null) {
                sb.append(data);
                Log.i(TAG, "C: Received " + sb.toString());
                stuff = sb.toString();
            }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// close socket!!
			try {
				out.close();
				in.close();
				s.close();
			} catch (IOException e) {
			}
		}
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public String getStuff() {
		return stuff;
	}
}
