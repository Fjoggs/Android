package vegardfj.oving7.klient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText inputNumber1;
	EditText inputNumber2;
	TextView outputAnswer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNumber1 = (EditText) findViewById(R.id.editText1);
        inputNumber2 = (EditText) findViewById(R.id.editText2);
        outputAnswer = (TextView) findViewById(R.id.textView1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onClickSendTall(View v) {
    	int number1 = Integer.parseInt(inputNumber1.getText().toString());
    	int number2 = Integer.parseInt(inputNumber2.getText().toString());
    	if(number1 != 0 && number2 != 0) {
    		Client client = new Client(mainHandler, number1, number2);
       		client.start();
    	}   	
    }

    public Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                // updates the TextView with the received text
            	int number3 = msg.getData().getInt("number3");
            	String outputText = Integer.toString(number3);
                outputAnswer.setText(outputText);
            }
        };
    };
}
