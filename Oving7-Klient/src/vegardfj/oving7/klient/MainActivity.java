package vegardfj.oving7.klient;

import android.app.Activity;
import android.os.Bundle;
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
    		new Client(number1, number2).start();
    		outputAnswer.setText(number1+" "+number2);
    	} else {
    		outputAnswer.setText("Please enter numbars that isnt zero!");
    	}
    	
    }
}
