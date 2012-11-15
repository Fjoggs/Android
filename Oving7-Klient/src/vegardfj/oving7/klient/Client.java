package vegardfj.oving7.klient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Client extends Thread {
	private final static String TAG = "Client";
	private final static String IP = "10.0.2.2";
	private final static int PORT = 12345;
	private Handler handler;
	private int number1;
	private int number2;
	private int number3;
	
    private Handler clientHandler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                number1 = msg.getData().getInt("number1");
                number2 = msg.getData().getInt("number2");
            }
        }
    };

	public Client(Handler handler) {
		this.handler = handler;
		number3 = 0;
	}
	
	public void run() {
		Socket s = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			s = new Socket(IP, PORT);
			Log.v(TAG, "C: Connected to server: " + s.toString());
			out = new DataOutputStream(s.getOutputStream());
			in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
			out.writeInt(number1);
			out.writeInt(number2);
			out.flush();
			Log.i(TAG, "C: Sent to server: " +number1+ ", "+number2);
			Log.i(TAG, "C: Recieved: "+in.readUTF());
			number3 = in.readInt();
			Log.i(TAG, "C: Recieved: "+number3);
            Message messageToParent = new Message();
            Bundle messageData = new Bundle();
            messageToParent.what = 0;
            messageData.putInt("number3", number3);
            messageToParent.setData(messageData);
            handler.sendMessage(messageToParent);
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
	
	public int getNumber3() {
		return number3;
	}
	
	public Handler getClientHandler() {
		return clientHandler;
	}
}
