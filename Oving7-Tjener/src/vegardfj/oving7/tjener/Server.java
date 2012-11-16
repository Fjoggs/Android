package vegardfj.oving7.tjener;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class Server extends Thread {
	private final static String TAG = "ServerThread";
	private final static int PORT = 12345;
	private static int number1 = 0;
	private static int number2 = 0;
	private static int number3 = 0;

	public void run() {
		ServerSocket ss = null;
		Socket s = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		String toClient = "Welcome client..";
		try {
			while (true) {
				Log.i(TAG, "start server....");
				ss = new ServerSocket(PORT);
				Log.i(TAG, "serversocket created, wait for client....");
				s = ss.accept();
				Log.v(TAG, "client connected...");
				out = new DataOutputStream(s.getOutputStream());
				in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				out.writeUTF(toClient);// send text to client
				out.flush();
				Log.i(TAG, "C: Sent to client: " + toClient);
				number1 = in.readInt();
				number2 = in.readInt();
				number3 = number1 + number2;
				out.writeInt(number3);
				out.flush();
				Log.i(TAG, "C: Recieved: " + number1 + "\n");
				Log.i(TAG, "C: Recieved: " + number2 + "\n");
				Log.i(TAG, "C: Sent to client: " + number3 + "\n");
				out.close();
				in.close();
				s.close();
				ss.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {// close sockets!!
			try {
				out.close();
				in.close();
				s.close();
				ss.close();
			} catch (Exception e) {
			}
		}
	}
}
